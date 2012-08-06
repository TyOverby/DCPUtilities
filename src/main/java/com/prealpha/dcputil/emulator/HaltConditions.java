package com.prealpha.dcputil.emulator;

/**
 * User: Ty
 * Date: 8/6/12
 * Time: 12:02 AM
 */
public enum HaltConditions {
    BREAK(new Condition() {
        @Override
        public boolean isMet(Machine machine) {
            return (machine.memory[machine.pc]==Opcodes.BRK);
        }
    }),
    NOOP(new Condition() {
        @Override
        public boolean isMet(Machine machine) {
            return (machine.memory[machine.pc]==0x00);
        }
    }),
//    TIME(new Condition() {
//        @Override
//        public boolean isMet(Machine machine) {
//            return false;  // TODO: Finish this
//        }
//    }),
    NEAR_END(new Condition() {
        @Override
        public boolean isMet(Machine machine) {
            return machine.pc>=(machine.memory.length-4);
        }
    });

    private Condition condition;
    private HaltConditions(Condition condition){
        this.condition = condition;
    }

    public boolean isMet(Machine machine){
        return this.condition.isMet(machine);
    }

    private interface Condition{
        public boolean isMet(Machine machine);
    }
}
