package com.github.zxh.classpy.classfile.bytecode;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.classfile.reader.ClassReader;

/**
 * Base class for all instructions.
 */
public class Instruction extends ClassComponent {

    protected final Opcode opcode;
    protected final int pc;

    public Instruction(Opcode opcode, int pc) {
        this.opcode = opcode;
        this.pc = pc;
        setDesc(opcode.name());
    }

    public int getPc() {
        return pc;
    }
    
    @Override
    protected final void readContent(ClassReader reader) {
        reader.readU1(); // opcode
        readOperands(reader);
    }
    
    protected void readOperands(ClassReader reader) {
        if (opcode.operandCount > 0) {
            reader.skipBytes(opcode.operandCount);
        }
    }

}
