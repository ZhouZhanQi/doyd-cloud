package com.doyd.core.util.http;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;

/**
 * 文件byte数组
 *
 * @author ZhouZQ
 * @create 2019/3/22
 */
@Getter
@Setter
public class FileBytesPart {

    private String filename;

    private byte[] data;

    public static Builder custom() {
        return new Builder();
    }

    public static class Builder {
        private String filename;
        private byte[] data;

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        public FileBytesPart build() {
            FileBytesPart part = new FileBytesPart();
            part.setFilename(this.filename);
            part.setData(this.data);
            return part;
        }
    }


    public FileInputStreamPart toInputStreamPart() {
        if (StringUtils.isBlank(filename) || data == null || data.length == 0) {
            return null;
        }
        FileInputStreamPart insPart = new FileInputStreamPart();
        insPart.setFilename(filename);
        insPart.setData(new ByteArrayInputStream(data));
        return insPart;
    }

}
