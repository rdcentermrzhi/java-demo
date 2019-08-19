package com.rdcentermrzhi.java.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class LongEventProducerWithTranslator {

	private RingBuffer<ValueEvent> ringBuffer;

	public LongEventProducerWithTranslator(RingBuffer<ValueEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorOneArg<ValueEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<ValueEvent, ByteBuffer>() {

		@Override
		public void translateTo(ValueEvent event, long sequence, ByteBuffer arg0) {

			event.setValue(arg0.getLong());
		}

	};

	public void onData(ByteBuffer bb) {
		ringBuffer.publishEvent(TRANSLATOR, bb);
	}
}
