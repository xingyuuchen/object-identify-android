// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netscenegethotsearch.proto

package com.cxy.oi.autogen;

public final class NetSceneGetHotSearchProto {
  private NetSceneGetHotSearchProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netscenegethotsearch_NetSceneGetHotSearchReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netscenegethotsearch_NetSceneGetHotSearchReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_HotSearchItem_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_HotSearchItem_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032netscenegethotsearch.proto\022\024netscenege" +
      "thotsearch\")\n\027NetSceneGetHotSearchReq\022\016\n" +
      "\006usr_id\030\001 \001(\r\"\267\002\n\030NetSceneGetHotSearchRe" +
      "sp\022V\n\020hot_search_items\030\001 \003(\0132<.netsceneg" +
      "ethotsearch.NetSceneGetHotSearchResp.Hot" +
      "SearchItem\032\302\001\n\rHotSearchItem\022\021\n\titem_nam" +
      "e\030\001 \001(\t\022_\n\titem_type\030\002 \001(\0162E.netsceneget" +
      "hotsearch.NetSceneGetHotSearchResp.HotSe" +
      "archItem.ItemType:\005PLANT\022\014\n\004heat\030\003 \001(\r\"/" +
      "\n\010ItemType\022\t\n\005PLANT\020\000\022\n\n\006ANIMAL\020\001\022\014\n\010LAN" +
      "DMARK\020\002B1\n\022com.cxy.oi.autogenB\031NetSceneG" +
      "etHotSearchProtoP\001"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_netscenegethotsearch_NetSceneGetHotSearchReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_netscenegethotsearch_NetSceneGetHotSearchReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netscenegethotsearch_NetSceneGetHotSearchReq_descriptor,
        new java.lang.String[] { "UsrId", });
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_descriptor,
        new java.lang.String[] { "HotSearchItems", });
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_HotSearchItem_descriptor =
      internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_descriptor.getNestedTypes().get(0);
    internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_HotSearchItem_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_netscenegethotsearch_NetSceneGetHotSearchResp_HotSearchItem_descriptor,
        new java.lang.String[] { "ItemName", "ItemType", "Heat", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
