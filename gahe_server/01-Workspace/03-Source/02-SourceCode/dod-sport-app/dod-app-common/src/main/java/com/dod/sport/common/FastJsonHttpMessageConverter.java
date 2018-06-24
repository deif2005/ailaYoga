//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dod.sport.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dod.sport.domain.common.ResponseT;
import com.dod.sport.domain.common.RtnCodeEnum;
import com.dod.sport.domain.common.StringWrapper;
import com.dod.sport.util.Message;
import com.dod.sport.util.ReturnResult;
import com.sun.javafx.collections.MappingChange;
import net.sf.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class FastJsonHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset;
    private SerializerFeature[] features;
    //  private ITypeReference iTypeReference;

    public FastJsonHttpMessageConverter() {
        super(new MediaType[]{new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8)});
        this.charset = UTF8;
        this.features = new SerializerFeature[0];
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return this.features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    /*  public ITypeReference getiTypeReference() {
          return this.iTypeReference;
      }

      public void setiTypeReference(ITypeReference iTypeReference) {
          this.iTypeReference = iTypeReference;
      }
  */
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];

        while(true) {
            int bytes = in.read(buf);
            if(bytes == -1) {
                byte[] bytes1 = baos.toByteArray();
                String url = ((ServletServerHttpRequest)inputMessage).getServletRequest().getServletPath();
                int size = url.indexOf("?");
                if(size != -1) {
                    url = url.substring(0, size);
                }

                return JSON.parseObject(bytes1, 0, bytes1.length, this.charset.newDecoder(), clazz, new Feature[0]);
            }

            if(bytes > 0) {
                baos.write(buf, 0, bytes);
            }
        }
    }

//    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        ResponseT response = new ResponseT(RtnCodeEnum.SUCCESS);
//        if(t instanceof StringWrapper) {
//            StringWrapper sw = (StringWrapper) t ;
//            response.setMsg(sw.getS());
//        }else{
//            response.setBizData(t);
//        }
//        OutputStream out = outputMessage.getBody();
//        String text = JSONObject.fromObject(response).toString();
//        //  text = URLEncoder.encode(text,"UTF-8");
//        byte[] bytes = text.getBytes(this.charset);
//        out.write(bytes);
//    }

    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = "";
        Map<String,Object> map = new HashMap<String,Object>();
        Message message = new Message();
        ReturnResult result = new ReturnResult();
        if (t != null && !t.toString().equals("")) {
            message.setDatas(t);
            message.setResult(result);
            text = JSONObject.fromObject(message).toString();
        }else{
            map.put("result",result);
            text = JSONObject.fromObject(map).toString();

        }
//        text = URLEncoder.encode(text, "UTF-8");
        byte[] bytes = text.getBytes(this.charset);
        out.write(bytes);
    }
}
