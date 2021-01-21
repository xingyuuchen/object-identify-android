// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netscenequeryimg.proto

package com.cxy.oi.autogen;

/**
 * Protobuf type {@code netscenequeryimg.NetSceneQueryImgReq}
 */
public final class NetSceneQueryImgReq extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:netscenequeryimg.NetSceneQueryImgReq)
    NetSceneQueryImgReqOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NetSceneQueryImgReq.newBuilder() to construct.
  private NetSceneQueryImgReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NetSceneQueryImgReq() {
    imgBytes_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new NetSceneQueryImgReq();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private NetSceneQueryImgReq(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            bitField0_ |= 0x00000001;
            imgBytes_ = input.readBytes();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.cxy.oi.autogen.NetSceneQueryImgProto.internal_static_netscenequeryimg_NetSceneQueryImgReq_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cxy.oi.autogen.NetSceneQueryImgProto.internal_static_netscenequeryimg_NetSceneQueryImgReq_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cxy.oi.autogen.NetSceneQueryImgReq.class, com.cxy.oi.autogen.NetSceneQueryImgReq.Builder.class);
  }

  private int bitField0_;
  public static final int IMG_BYTES_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString imgBytes_;
  /**
   * <code>optional bytes img_bytes = 1;</code>
   * @return Whether the imgBytes field is set.
   */
  @java.lang.Override
  public boolean hasImgBytes() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional bytes img_bytes = 1;</code>
   * @return The imgBytes.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getImgBytes() {
    return imgBytes_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeBytes(1, imgBytes_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, imgBytes_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.cxy.oi.autogen.NetSceneQueryImgReq)) {
      return super.equals(obj);
    }
    com.cxy.oi.autogen.NetSceneQueryImgReq other = (com.cxy.oi.autogen.NetSceneQueryImgReq) obj;

    if (hasImgBytes() != other.hasImgBytes()) return false;
    if (hasImgBytes()) {
      if (!getImgBytes()
          .equals(other.getImgBytes())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasImgBytes()) {
      hash = (37 * hash) + IMG_BYTES_FIELD_NUMBER;
      hash = (53 * hash) + getImgBytes().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.cxy.oi.autogen.NetSceneQueryImgReq parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.cxy.oi.autogen.NetSceneQueryImgReq prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code netscenequeryimg.NetSceneQueryImgReq}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:netscenequeryimg.NetSceneQueryImgReq)
      com.cxy.oi.autogen.NetSceneQueryImgReqOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.cxy.oi.autogen.NetSceneQueryImgProto.internal_static_netscenequeryimg_NetSceneQueryImgReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cxy.oi.autogen.NetSceneQueryImgProto.internal_static_netscenequeryimg_NetSceneQueryImgReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cxy.oi.autogen.NetSceneQueryImgReq.class, com.cxy.oi.autogen.NetSceneQueryImgReq.Builder.class);
    }

    // Construct using com.cxy.oi.autogen.NetSceneQueryImgReq.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      imgBytes_ = com.google.protobuf.ByteString.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.cxy.oi.autogen.NetSceneQueryImgProto.internal_static_netscenequeryimg_NetSceneQueryImgReq_descriptor;
    }

    @java.lang.Override
    public com.cxy.oi.autogen.NetSceneQueryImgReq getDefaultInstanceForType() {
      return com.cxy.oi.autogen.NetSceneQueryImgReq.getDefaultInstance();
    }

    @java.lang.Override
    public com.cxy.oi.autogen.NetSceneQueryImgReq build() {
      com.cxy.oi.autogen.NetSceneQueryImgReq result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cxy.oi.autogen.NetSceneQueryImgReq buildPartial() {
      com.cxy.oi.autogen.NetSceneQueryImgReq result = new com.cxy.oi.autogen.NetSceneQueryImgReq(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        to_bitField0_ |= 0x00000001;
      }
      result.imgBytes_ = imgBytes_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.cxy.oi.autogen.NetSceneQueryImgReq) {
        return mergeFrom((com.cxy.oi.autogen.NetSceneQueryImgReq)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cxy.oi.autogen.NetSceneQueryImgReq other) {
      if (other == com.cxy.oi.autogen.NetSceneQueryImgReq.getDefaultInstance()) return this;
      if (other.hasImgBytes()) {
        setImgBytes(other.getImgBytes());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.cxy.oi.autogen.NetSceneQueryImgReq parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.cxy.oi.autogen.NetSceneQueryImgReq) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.ByteString imgBytes_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes img_bytes = 1;</code>
     * @return Whether the imgBytes field is set.
     */
    @java.lang.Override
    public boolean hasImgBytes() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional bytes img_bytes = 1;</code>
     * @return The imgBytes.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getImgBytes() {
      return imgBytes_;
    }
    /**
     * <code>optional bytes img_bytes = 1;</code>
     * @param value The imgBytes to set.
     * @return This builder for chaining.
     */
    public Builder setImgBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
      imgBytes_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes img_bytes = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearImgBytes() {
      bitField0_ = (bitField0_ & ~0x00000001);
      imgBytes_ = getDefaultInstance().getImgBytes();
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:netscenequeryimg.NetSceneQueryImgReq)
  }

  // @@protoc_insertion_point(class_scope:netscenequeryimg.NetSceneQueryImgReq)
  private static final com.cxy.oi.autogen.NetSceneQueryImgReq DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.cxy.oi.autogen.NetSceneQueryImgReq();
  }

  public static com.cxy.oi.autogen.NetSceneQueryImgReq getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  @java.lang.Deprecated public static final com.google.protobuf.Parser<NetSceneQueryImgReq>
      PARSER = new com.google.protobuf.AbstractParser<NetSceneQueryImgReq>() {
    @java.lang.Override
    public NetSceneQueryImgReq parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new NetSceneQueryImgReq(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NetSceneQueryImgReq> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NetSceneQueryImgReq> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cxy.oi.autogen.NetSceneQueryImgReq getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

