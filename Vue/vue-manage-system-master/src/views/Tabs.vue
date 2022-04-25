<template>
  <div class="">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="iconfont">&#xe603;</i>核酸通知</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-tabs v-model="message">
        <el-tab-pane :label="`未读消息(${state.unread.length})`" name="first">
          <el-table :data="state.unread" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title">{{scope.row.title}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="date" width="180"></el-table-column>
            <el-table-column width="120">
              <template #default="scope">
                <el-button size="small" @click="handleRead(scope.$index)">标为已读</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="primary" @click="handleAllUnRead">全部标为已读</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane :label="`已读消息(${state.read.length})`" name="second">
          <template v-if="message === 'second'">
            <el-table :data="state.read" :show-header="false" style="width: 100%">
              <el-table-column>
                <template #default="scope">
                  <span class="message-title">{{scope.row.title}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="date" width="150"></el-table-column>
              <el-table-column width="120">
                <template #default="scope">
                  <el-button type="danger" @click="handleDel(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-row">
              <el-button type="danger" @click = "handleAllDel()">删除全部</el-button>
            </div>
          </template>
        </el-tab-pane>
        <el-tab-pane :label="`回收站(${state.recycle.length})`" name="third">
          <template v-if="message === 'third'">
            <el-table :data="state.recycle" :show-header="false" style="width: 100%">
              <el-table-column>
                <template #default="scope">
                  <span class="message-title">{{scope.row.title}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="date" width="150"></el-table-column>
              <el-table-column width="120">
                <template #default="scope">
                  <el-button @click="handleRestore(scope.$index)">还原</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-row">
              <el-button type="danger" @click ="removeAll()">清空回收站</el-button>
            </div>
          </template>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import request from "../utils/request";



export default {
  name: "tabs",

  setup() {
    const message = ref("first");
    const state = reactive({
      unread: [],
      read: [],
      recycle: [],
    });
    const newUnread = () => {
      let data = {
        date: "",
        title: "",
      };
      state.unread.push(data);
    };
    const getData = () => {
      request({
        url: "http://localhost:8080/acid",
        method: "get",
      }).then(res => {
        console.log(res);
        for (var l = 0; l < res.length; l++) {
          newUnread();
          state.unread[l].date = res[l].startTime;
          state.unread[l].title = "将于" + res[l].endTime + "开展对于" + res[l].address + "全体人员的核酸测试";
        }
      })

    };
    getData();
    const handleAllUnRead = () =>
    {
      state.read = state.unread;
      state.unread = [];
    };
    const handleRead = (index) => {
      const item = state.unread.splice(index, 1);
      console.log(item);
      state.read = item.concat(state.read);
    };
    const handleDel = (index) => {
      const item = state.read.splice(index, 1);
      state.recycle = item.concat(state.recycle);
    };
    const handleRestore = (index) => {
      const item = state.recycle.splice(index, 1);
      state.read = item.concat(state.read);
    };
    const handleAllDel = () => {
      state.recycle = state.read;
      state.read = [];
    };
    const removeAll = () => {
      state.recycle = [];
    };
    return {
      message,
      state,
      handleAllUnRead,
      handleRead,
      handleDel,
      handleRestore,
      handleAllDel,
      removeAll,

    };
  }
};
</script>

<style>
.message-title {
  cursor: pointer;
}
.handle-row {
  margin-top: 30px;
}
</style>

