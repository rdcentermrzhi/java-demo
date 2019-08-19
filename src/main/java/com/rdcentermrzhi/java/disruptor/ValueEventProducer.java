package com.rdcentermrzhi.java.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class ValueEventProducer {

	private  RingBuffer<ValueEvent> ringBuffer;
	
	public ValueEventProducer(RingBuffer<ValueEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	
	public void onData(ByteBuffer bb) {
		long  sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            ValueEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
                                                        // for the sequence
            event.setValue(bb.getLong(0));  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
	}
	
	
	
	
	
}
