package org.treytomes.notepad.resources;

public enum ImageSize {

	Size16(16),
	Size24(24),
	Size32(32),
	Size48(48),
	Size256(256);
	
	private int _size;
	
	ImageSize(int size) {
		_size = size;
	}
	
	public int getSize() {
		return _size;
	}
}