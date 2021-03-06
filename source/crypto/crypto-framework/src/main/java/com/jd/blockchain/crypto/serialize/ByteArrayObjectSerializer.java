package com.jd.blockchain.crypto.serialize;

import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.jd.blockchain.crypto.HashDigest;
import com.jd.blockchain.crypto.PubKey;
import com.jd.blockchain.crypto.SignatureDigest;
import com.jd.blockchain.utils.Bytes;
import com.jd.blockchain.utils.io.BytesSlice;

public class ByteArrayObjectSerializer implements ObjectSerializer {

    private Class<?> clazz;

    private ByteArrayObjectSerializer(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static ByteArrayObjectSerializer getInstance(Class<?> clazz) {
        return new ByteArrayObjectSerializer(clazz);
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object.getClass() != clazz) {
            serializer.writeNull();
            return;
        }
        if (object instanceof HashDigest) {
            serializer.write(new HashDigestJson(((HashDigest) object).toBase58()));
        } else if (object instanceof PubKey) {
            serializer.write(new HashDigestJson(((PubKey) object).toBase58()));
        } else if (object instanceof SignatureDigest) {
            serializer.write(new HashDigestJson(((SignatureDigest) object).toBase58()));
        } else if (object instanceof Bytes) {
            serializer.write(new HashDigestJson(((Bytes) object).toBase58()));
        } else if (object instanceof BytesSlice) {
            byte[] bytes = ((BytesSlice) object).toBytes();
            serializer.write(new HashDigestJson(new String(bytes)));
        }
    }

    private static class HashDigestJson {

        String value;

        public HashDigestJson(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
		public String getValue() {
            return value;
        }

        @SuppressWarnings("unused")
		public void setValue(String value) {
            this.value = value;
        }
    }
}
