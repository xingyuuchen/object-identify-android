// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: basenetscenereq.proto

package com.cxy.oi.autogen;

public interface BaseNetSceneReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:basenetscenereq.BaseNetSceneReq)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 net_scene_type = 1;</code>
   * @return Whether the netSceneType field is set.
   */
  boolean hasNetSceneType();
  /**
   * <code>optional int32 net_scene_type = 1;</code>
   * @return The netSceneType.
   */
  int getNetSceneType();

  /**
   * <code>optional bytes net_scene_req_buff = 2;</code>
   * @return Whether the netSceneReqBuff field is set.
   */
  boolean hasNetSceneReqBuff();
  /**
   * <code>optional bytes net_scene_req_buff = 2;</code>
   * @return The netSceneReqBuff.
   */
  com.google.protobuf.ByteString getNetSceneReqBuff();
}