// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netscenechangenickname.proto

package com.cxy.oi.autogen;

public interface NetSceneChangeNicknameReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:netscenechangenickname.NetSceneChangeNicknameReq)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional uint32 usr_id = 1;</code>
   * @return Whether the usrId field is set.
   */
  boolean hasUsrId();
  /**
   * <code>optional uint32 usr_id = 1;</code>
   * @return The usrId.
   */
  int getUsrId();

  /**
   * <code>optional string nickname = 2;</code>
   * @return Whether the nickname field is set.
   */
  boolean hasNickname();
  /**
   * <code>optional string nickname = 2;</code>
   * @return The nickname.
   */
  java.lang.String getNickname();
  /**
   * <code>optional string nickname = 2;</code>
   * @return The bytes for nickname.
   */
  com.google.protobuf.ByteString
      getNicknameBytes();
}
