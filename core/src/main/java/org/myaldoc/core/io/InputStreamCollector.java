package org.myaldoc.core.io;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.SequenceInputStream;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 05/01/2019
 * @Class purposes : .......
 */
@AllArgsConstructor
@NoArgsConstructor
public class InputStreamCollector {
    private InputStream is;

    public void collectInputStream(InputStream is) {
        if (this.is == null) this.is = is;
        this.is = new SequenceInputStream(this.is, is);
    }

    public InputStream getInputStream() {
        return this.is;
    }
}
