package com.saintsrobotics.corebot.output;

public class TestBoardMotors extends Motors {
    
    @Override public int getGearDropOut() { return -1; }
    @Override public int getGearDropIn() { return -1; }
    
    public TestBoardMotors() {
        super(0, 2, 1, 3, 7, 6, 5, 4,
                false,
                false,
                false,
                false);
    }

	@Override
	public int getGearDropOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGearDropIn() {
		// TODO Auto-generated method stub
		return 0;
	}
}