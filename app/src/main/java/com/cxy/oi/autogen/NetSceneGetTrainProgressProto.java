// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netscenegettrainprogress.proto

package com.cxy.oi.autogen;

public final class NetSceneGetTrainProgressProto {
  private NetSceneGetTrainProgressProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netscenegettrainprogress_NetSceneGetTrainProgressReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressResp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netscenegettrainprogress_NetSceneGetTrainProgressResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036netscenegettrainprogress.proto\022\030netsce" +
      "negettrainprogress\"*\n\033NetSceneGetTrainPr" +
      "ogressReq\022\013\n\003nop\030\001 \001(\010\"n\n\034NetSceneGetTra" +
      "inProgressResp\022\022\n\nis_running\030\001 \001(\010\022\022\n\ncu" +
      "rr_epoch\030\002 \001(\005\022\023\n\013total_epoch\030\003 \001(\005\022\021\n\th" +
      "it_rates\030\004 \003(\002B5\n\022com.cxy.oi.autogenB\035Ne" +
      "tSceneGetTrainProgressProtoP\001"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netscenegettrainprogress_NetSceneGetTrainProgressReq_descriptor,
        new java.lang.String[] { "Nop", });
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressResp_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_netscenegettrainprogress_NetSceneGetTrainProgressResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netscenegettrainprogress_NetSceneGetTrainProgressResp_descriptor,
        new java.lang.String[] { "IsRunning", "CurrEpoch", "TotalEpoch", "HitRates", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
