package com.github.zxh.classpy.classfile.datatype;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.classfile.ClassParseException;
import com.github.zxh.classpy.classfile.reader.ClassReader;
import com.github.zxh.classpy.classfile.helper.StringUtil;
import com.github.zxh.classpy.classfile.attribute.AttributeInfo;

/**
 * Array of class components.
 */
public class Table extends ClassComponent {

    private final Class<? extends ClassComponent> entryClass;
    private final UInt length;

    public Table(Class<? extends ClassComponent> entryClass, UInt length) {
        this.entryClass = entryClass;
        this.length = length;
    }
    
    @Override
    protected void readContent(ClassReader reader) {
        try {
            for (int i = 0; i < length.getValue(); i++) {
                ClassComponent c = readEntry(reader);
                super.addSubComponent(c);
            }
        } catch (ReflectiveOperationException e) {
            throw new ClassParseException(e);
        }
    }

    private ClassComponent readEntry(ClassReader reader) throws ReflectiveOperationException {
        if (entryClass == AttributeInfo.class) {
            return readAttributeInfo(reader);
        } else {
            ClassComponent c = entryClass.newInstance();
            c.read(reader);
            return c;
        }
    }
    
    private AttributeInfo readAttributeInfo(ClassReader reader) {
        int attrNameIndex = reader.getByteBuffer().getShort(reader.getPosition());
        String attrName = reader.getConstantPool().getUtf8String(attrNameIndex);
        
        AttributeInfo attr = AttributeInfo.create(attrName);
        attr.setName(attrName);
        attr.read(reader);
        
        return attr;
    }

    @Override
    protected void afterRead(ClassReader reader) {
        for (int i = 0; i < length.getValue(); i++) {
            ClassComponent entry = super.getSubComponent(i);

            String newName = StringUtil.formatIndex(length.getValue(), i);
            String oldName = entry.getName();
            if (oldName != null) {
                newName += " (" + oldName + ")";
            }
            entry.setName(newName);
        }
    }

}