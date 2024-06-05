package com.pi4j.mvc.powerhouse;

public enum I2CPin {
    E1(0b0000_0001), E2(0b0000_0010), E3(0b0000_0100), E4(0b0000_1000),
    E5(0b0001_0000), E6(0b0010_0000), E7(0b0100_0000), E8(0b1000_0000);

    private final int address;

    I2CPin(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }
}
