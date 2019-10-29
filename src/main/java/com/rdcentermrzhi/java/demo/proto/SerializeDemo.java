// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protobuf/SerializeDemo.proto

package com.rdcentermrzhi.java.demo.proto;

public final class SerializeDemo {
  private SerializeDemo() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface StudentOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Student)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional int32 a = 1;</code>
     */
    int getA();

    /**
     * <code>optional int32 b = 2;</code>
     */
    int getB();

    /**
     * <code>optional int32 c = 3;</code>
     */
    int getC();

    /**
     * <code>optional string d = 4;</code>
     */
    java.lang.String getD();
    /**
     * <code>optional string d = 4;</code>
     */
    com.google.protobuf.ByteString
        getDBytes();

    /**
     * <code>optional int64 g = 5;</code>
     */
    long getG();

    /**
     * <code>optional float f = 6;</code>
     */
    float getF();
  }
  /**
   * Protobuf type {@code Student}
   */
  public  static final class Student extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Student)
      StudentOrBuilder {
    // Use Student.newBuilder() to construct.
    private Student(com.google.protobuf.GeneratedMessage.Builder builder) {
      super(builder);
    }
    private Student() {
      a_ = 0;
      b_ = 0;
      c_ = 0;
      d_ = "";
      g_ = 0L;
      f_ = 0F;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Student(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              a_ = input.readInt32();
              break;
            }
            case 16: {

              b_ = input.readInt32();
              break;
            }
            case 24: {

              c_ = input.readInt32();
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();

              d_ = bs;
              break;
            }
            case 40: {

              g_ = input.readInt64();
              break;
            }
            case 53: {

              f_ = input.readFloat();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw new RuntimeException(e.setUnfinishedMessage(this));
      } catch (java.io.IOException e) {
        throw new RuntimeException(
            new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this));
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.rdcentermrzhi.java.demo.proto.SerializeDemo.internal_static_Student_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.rdcentermrzhi.java.demo.proto.SerializeDemo.internal_static_Student_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.class, com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.Builder.class);
    }

    public static final int A_FIELD_NUMBER = 1;
    private int a_;
    /**
     * <code>optional int32 a = 1;</code>
     */
    public int getA() {
      return a_;
    }

    public static final int B_FIELD_NUMBER = 2;
    private int b_;
    /**
     * <code>optional int32 b = 2;</code>
     */
    public int getB() {
      return b_;
    }

    public static final int C_FIELD_NUMBER = 3;
    private int c_;
    /**
     * <code>optional int32 c = 3;</code>
     */
    public int getC() {
      return c_;
    }

    public static final int D_FIELD_NUMBER = 4;
    private volatile java.lang.Object d_;
    /**
     * <code>optional string d = 4;</code>
     */
    public java.lang.String getD() {
      java.lang.Object ref = d_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          d_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string d = 4;</code>
     */
    public com.google.protobuf.ByteString
        getDBytes() {
      java.lang.Object ref = d_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        d_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int G_FIELD_NUMBER = 5;
    private long g_;
    /**
     * <code>optional int64 g = 5;</code>
     */
    public long getG() {
      return g_;
    }

    public static final int F_FIELD_NUMBER = 6;
    private float f_;
    /**
     * <code>optional float f = 6;</code>
     */
    public float getF() {
      return f_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (a_ != 0) {
        output.writeInt32(1, a_);
      }
      if (b_ != 0) {
        output.writeInt32(2, b_);
      }
      if (c_ != 0) {
        output.writeInt32(3, c_);
      }
      if (!getDBytes().isEmpty()) {
        output.writeBytes(4, getDBytes());
      }
      if (g_ != 0L) {
        output.writeInt64(5, g_);
      }
      if (f_ != 0F) {
        output.writeFloat(6, f_);
      }
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (a_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, a_);
      }
      if (b_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, b_);
      }
      if (c_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, c_);
      }
      if (!getDBytes().isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, getDBytes());
      }
      if (g_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, g_);
      }
      if (f_ != 0F) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(6, f_);
      }
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Student}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Student)
        com.rdcentermrzhi.java.demo.proto.SerializeDemo.StudentOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.rdcentermrzhi.java.demo.proto.SerializeDemo.internal_static_Student_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.rdcentermrzhi.java.demo.proto.SerializeDemo.internal_static_Student_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.class, com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.Builder.class);
      }

      // Construct using com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        a_ = 0;

        b_ = 0;

        c_ = 0;

        d_ = "";

        g_ = 0L;

        f_ = 0F;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.rdcentermrzhi.java.demo.proto.SerializeDemo.internal_static_Student_descriptor;
      }

      public com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student getDefaultInstanceForType() {
        return com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.getDefaultInstance();
      }

      public com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student build() {
        com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student buildPartial() {
        com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student result = new com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student(this);
        result.a_ = a_;
        result.b_ = b_;
        result.c_ = c_;
        result.d_ = d_;
        result.g_ = g_;
        result.f_ = f_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student) {
          return mergeFrom((com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student other) {
        if (other == com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student.getDefaultInstance()) return this;
        if (other.getA() != 0) {
          setA(other.getA());
        }
        if (other.getB() != 0) {
          setB(other.getB());
        }
        if (other.getC() != 0) {
          setC(other.getC());
        }
        if (!other.getD().isEmpty()) {
          d_ = other.d_;
          onChanged();
        }
        if (other.getG() != 0L) {
          setG(other.getG());
        }
        if (other.getF() != 0F) {
          setF(other.getF());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int a_ ;
      /**
       * <code>optional int32 a = 1;</code>
       */
      public int getA() {
        return a_;
      }
      /**
       * <code>optional int32 a = 1;</code>
       */
      public Builder setA(int value) {
        
        a_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 a = 1;</code>
       */
      public Builder clearA() {
        
        a_ = 0;
        onChanged();
        return this;
      }

      private int b_ ;
      /**
       * <code>optional int32 b = 2;</code>
       */
      public int getB() {
        return b_;
      }
      /**
       * <code>optional int32 b = 2;</code>
       */
      public Builder setB(int value) {
        
        b_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 b = 2;</code>
       */
      public Builder clearB() {
        
        b_ = 0;
        onChanged();
        return this;
      }

      private int c_ ;
      /**
       * <code>optional int32 c = 3;</code>
       */
      public int getC() {
        return c_;
      }
      /**
       * <code>optional int32 c = 3;</code>
       */
      public Builder setC(int value) {
        
        c_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 c = 3;</code>
       */
      public Builder clearC() {
        
        c_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object d_ = "";
      /**
       * <code>optional string d = 4;</code>
       */
      public java.lang.String getD() {
        java.lang.Object ref = d_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            d_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string d = 4;</code>
       */
      public com.google.protobuf.ByteString
          getDBytes() {
        java.lang.Object ref = d_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          d_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string d = 4;</code>
       */
      public Builder setD(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        d_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string d = 4;</code>
       */
      public Builder clearD() {
        
        d_ = getDefaultInstance().getD();
        onChanged();
        return this;
      }
      /**
       * <code>optional string d = 4;</code>
       */
      public Builder setDBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        d_ = value;
        onChanged();
        return this;
      }

      private long g_ ;
      /**
       * <code>optional int64 g = 5;</code>
       */
      public long getG() {
        return g_;
      }
      /**
       * <code>optional int64 g = 5;</code>
       */
      public Builder setG(long value) {
        
        g_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int64 g = 5;</code>
       */
      public Builder clearG() {
        
        g_ = 0L;
        onChanged();
        return this;
      }

      private float f_ ;
      /**
       * <code>optional float f = 6;</code>
       */
      public float getF() {
        return f_;
      }
      /**
       * <code>optional float f = 6;</code>
       */
      public Builder setF(float value) {
        
        f_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float f = 6;</code>
       */
      public Builder clearF() {
        
        f_ = 0F;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:Student)
    }

    // @@protoc_insertion_point(class_scope:Student)
    private static final com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student();
    }

    public static com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    public static final com.google.protobuf.Parser<Student> PARSER =
        new com.google.protobuf.AbstractParser<Student>() {
      public Student parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        try {
          return new Student(input, extensionRegistry);
        } catch (RuntimeException e) {
          if (e.getCause() instanceof
              com.google.protobuf.InvalidProtocolBufferException) {
            throw (com.google.protobuf.InvalidProtocolBufferException)
                e.getCause();
          }
          throw e;
        }
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Student> getParserForType() {
      return PARSER;
    }

    public com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_Student_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Student_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034protobuf/SerializeDemo.proto\"K\n\007Studen" +
      "t\022\t\n\001a\030\001 \001(\005\022\t\n\001b\030\002 \001(\005\022\t\n\001c\030\003 \001(\005\022\t\n\001d\030" +
      "\004 \001(\t\022\t\n\001g\030\005 \001(\003\022\t\n\001f\030\006 \001(\002B:\n!com.rdcen" +
      "termrzhi.java.demo.protoB\rSerializeDemo\252" +
      "\002\005Protob\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Student_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Student_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Student_descriptor,
        new java.lang.String[] { "A", "B", "C", "D", "G", "F", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}