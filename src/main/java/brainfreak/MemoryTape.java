package main.java.brainfreak;

public class MemoryTape {
	
	private byte[] memoryTape;
	
	private int memoryPosition = 0;
	private int memorySize = 30000;
	private boolean hasMemoryWrap = false;
	
	public MemoryTape() {
		memoryTape = new byte[memorySize];
	}
	
	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
		memoryTape = new byte[memorySize];
	}
	
	public int getMemorySize() {
		return memorySize;
	}
	
	public void setMemoryWrap(boolean hasMemoryWrap) {
		this.hasMemoryWrap = hasMemoryWrap;
	}
	
	public int getMemoryPosition() {
		return memoryPosition;
	}
	
	public int getValue() {
		return (int) memoryTape[memoryPosition];
	}
	
	public void setValue(byte value) {
		memoryTape[memoryPosition] = value;
	}
	
	public void incrementPosition() {
		memoryPosition++;
		memoryWrap();
	}
	
	public void decrementPosition() {
		memoryPosition--;
		memoryWrap();
	}
	
	public void incrementMemory() {
		memoryTape[memoryPosition]++;
	}
	
	public void decrementMemory() {
		memoryTape[memoryPosition]--;
	}
	
	private void memoryWrap() {
		if (hasMemoryWrap) {
			if (memoryPosition >= memoryTape.length) {
	    		memoryPosition %= memoryTape.length;
	        } else if (memoryPosition < 0) {
	        	memoryPosition += memoryTape.length;
	        }
		}
	}

}
