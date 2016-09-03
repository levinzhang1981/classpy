package com.github.zxh.classpy.classfile.attribute;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.classfile.datatype.U2;
import com.github.zxh.classpy.classfile.datatype.U2CpIndex;

/*
BootstrapMethods_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 num_bootstrap_methods;
    {   u2 bootstrap_method_ref;
        u2 num_bootstrap_arguments;
        u2 bootstrap_arguments[num_bootstrap_arguments];
    } bootstrap_methods[num_bootstrap_methods];
}
 */
public class BootstrapMethodsAttribute extends AttributeInfo {

    {
        super.addU2("numBootstrapMethods");
        super.addTable("bootstrapMethods", BootstrapMethodInfo.class);
    }

    
    public static class BootstrapMethodInfo extends ClassComponent {

        {
            super.add("bootstrapMethodRef", new U2CpIndex());
            super.addU2("numBootstrapArguments");
            super.addTable("bootstrapArguments", U2CpIndex.class);
        }
        
    }
    
}
