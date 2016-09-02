package com.github.zxh.classpy.classfile.datatype;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.classfile.reader.ClassReader;

public class U4Float extends ClassComponent {

    @Override
    protected void readContent(ClassReader reader) {
        float value = reader.readFloat();
        setDesc(Float.toString(value));
    }
    
}
