package com.rdcentermrzhi.java.demo.kryo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * 序列化编解码的对象 包信息不能变
 * @author 
 *
 */
public class KryoSerializationUtils {


    public KryoSerializationUtils() {  }

    /**
     * Kryo 的包装
     */
    private static class KryoHolder {
        private Kryo kryo;
        static final int BUFFER_SIZE = 1024;
        private Output output = new Output(BUFFER_SIZE, -1);     //reuse
        private Input input=new Input();
        KryoHolder(Kryo kryo) {
            this.kryo = kryo;
        }

        /**
         * @param kryo
         * @param clazz
         */
        private static void checkRegiterNeeded(Kryo kryo, Class<?> clazz) {
            kryo.register(clazz);
        }
    }


    interface KryoPool {

        /**
         * get o kryo object
         *
         * @return
         */
        KryoHolder get();

        /**
         * return object
         *
         * @param kryo
         */
        void offer(KryoHolder kryo);
    }


    //基于kryo序列换方案

    /**
     * 由于kryo创建的代价相对较高 ，这里使用空间换时间
     * 对KryoHolder对象进行重用
     * KryoHolder会出现峰值，应该不会造成内存泄漏哦
     */
    public static class KryoPoolImpl implements KryoPool {
        /**
         * default is 1500
         * online server limit 3K
         */
        // private static int DEFAULT_MAX_KRYO_SIZE = 1500;

        /**
         * thread safe list
         */
        private final BlockingQueue<KryoHolder> kryoFastTable = new LinkedBlockingQueue<KryoHolder>();


        /**
         *
         */
        private KryoPoolImpl() {

        }

        /**
         * @return
         */
        public static KryoPool getInstance() {
            return Singleton.pool;
        }

        /**
         * get o KryoHolder object
         *
         * @return
         */
        @Override
        public KryoHolder get() {
            KryoHolder kryoHolder = kryoFastTable.poll();       // Retrieves and removes the head of the queue represented by this table
            return kryoHolder == null ? creatInstnce() : kryoHolder;
        }

        /**
         * create a new kryo object to application use
         *
         * @return
         */
        public KryoHolder creatInstnce() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);//
            return new KryoHolder(kryo);
        }

        /**
         * return object
         * Inserts the specified element at the tail of this queue.
         *
         * @param kryoHolder
         */
        @Override
        public void offer(KryoHolder kryoHolder) {
            kryoFastTable.add(kryoHolder);
        }

        /**
         * creat a Singleton
         */
        private static class Singleton {
            private static final KryoPool pool = new KryoPoolImpl();
        }
    }


    /**
     * 将对象序列化为字节数组
     *
     * @param obj
     * @return 字节数组
     * @throws KryoCacheException
     */
    public static byte[] kryoSerialize(Object obj) throws KryoCacheException {
        KryoHolder kryoHolder = null;
        if (obj == null) throw new KryoCacheException("obj can not be null");
        try {
            kryoHolder = KryoPoolImpl.getInstance().get();
            kryoHolder.output.clear();  //rest outPut
            kryoHolder.kryo.writeClassAndObject(kryoHolder.output, obj);
            return kryoHolder.output.toBytes();
        } catch (KryoCacheException e) {
            throw new KryoCacheException("Serialize obj exception");
        } finally {
            KryoPoolImpl.getInstance().offer(kryoHolder);
            obj = null; //GC
        }
    }


    /**
     * 将字节数组反序列化为对象
     *
     * @param bytes 字节数组
     * @return object
     * @throws KryoCacheException
     */
    public static Object kryoDeserialize(byte[] bytes) throws KryoCacheException {
        KryoHolder kryoHolder = null;
        if (bytes == null) throw new KryoCacheException("bytes can not be null");
        try {
            kryoHolder = KryoPoolImpl.getInstance().get();
            kryoHolder.input.setBuffer(bytes,0,bytes.length);//call it ,and then use input object   ,data will be reset
            return kryoHolder.kryo.readClassAndObject(kryoHolder.input);
        } catch (KryoCacheException e) {
            throw new KryoCacheException("Deserialize bytes exception");
        } finally {
            KryoPoolImpl.getInstance().offer(kryoHolder);
            bytes = null;
        }
    }
    

}