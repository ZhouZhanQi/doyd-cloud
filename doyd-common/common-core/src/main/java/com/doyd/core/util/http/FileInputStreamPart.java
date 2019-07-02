package com.doyd.core.util.http;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * 文件流
 *
 * @author ZhouZQ
 * @create 2019/3/22
 */
@Getter
@Setter
public class FileInputStreamPart {

    private String filename;

    private InputStream data;

    public static Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private String filename;

        private InputStream data;

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder data(InputStream data) {
            this.data = data;
            return this;
        }

        public FileInputStreamPart build() {
            FileInputStreamPart part = new FileInputStreamPart();
            part.setFilename(this.filename);
            part.setData(this.data);
            return part;
        }
    }
}
