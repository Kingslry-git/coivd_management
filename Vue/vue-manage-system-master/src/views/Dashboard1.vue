<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" class="mgb20" style="height:252px;">
          <div class="user-info">
            <img src="../assets/img/img.jpg" class="user-avator" alt />
            <div class="user-info-cont">
              <div class="user-info-name">{{ name }}</div>
              <div>{{ role }}</div>
            </div>
          </div>
          <div class="user-info-list">
            上次登录时间：
            <span>2019-11-01</span>
          </div>
          <div class="user-info-list">
            上次登录地点：
            <span>东莞</span>
          </div>
        </el-card>
        <el-card shadow="hover" style="height:252px;">
          <template #header>
            <div class="clearfix">
              <span style="font-family:myff ">龙江疫情阶段概览</span>
            </div>
          </template>
          健康
          <el-progress :percentage="71.3" color="#42b983"></el-progress>
          患病
          <el-progress :percentage="24.1" color="#f1e05a"></el-progress>
          无症状
          <el-progress :percentage="13.7"></el-progress>
          死亡
          <el-progress :percentage="5.9" color="#f56c6c"></el-progress>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-user-solid grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ heath_num }}</div>
                  <div>健康人数</div>
                </div>
              </div>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-message-solid grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">321</div>
                  <div>系统消息</div>
                </div>
              </div>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-s-goods grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">5000</div>
                  <div>数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card shadow="hover" style="height:403px;">
              <div ref="demoh"   Class="schart"></div>

        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Schart from "vue-schart";
import { reactive,ref,onMounted,toRefs } from "vue";
import echarts from "echarts"
import "../assets/heilongjiang"
import {mapdata} from  "../assets/mapdata.js";
export default {
  name: "dashboard",
  components: { Schart,echarts },
  mounted() {
    let mycharts = echarts.init(this.$refs.demoh);
    echarts.registerMap("heilongjiangMap", mapdata)
    let option5 =  {
      title: {
        text: '黑龙江省疫情分布情况',
      },
      series: [
        {
          name: '黑龙江省疫情分布',
          type: 'map',
          map: 'heilongjiangMap',
          label: {
            show: true
          },
          data: [
            { name: '大兴安岭地区', value: 3 },
            { name: '黑河市', value: 297},
            { name: '齐齐哈尔市', value: 45},
            { name: '绥化市', value: 539 },
            { name: '大庆市', value: 37 },
            { name: '哈尔滨市', value: 76 },
            { name: '伊春市', value: 1 },
            { name: '鹤岗市', value: 5 },
            { name: '佳木斯市', value: 56},
            { name: '双鸭山市', value: 52 },
            { name: '七台河市', value: 18 },
            { name: '牡丹江市', value: 91 },
            { name: '鸡西市', value: 52 },
          ],


        }
      ],
      tooltip: {
        trigger: 'item',
        formatter: '{b}<br/>{c}'
      },
      toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
          dataView: { readOnly: false },
          restore: {},
          saveAsImage: {}
        }
      },
      visualMap: {
        min: 0,
        max: 800,
        text: ['High', 'Low'],
        realtime: false,
        calculable: true,
        inRange: {
          color: ['lightskyblue', 'yellow', 'orangered']
        }
      },

    };
    mycharts.setOption(option5)
  },
  // echarts.registerMap('heilongjiang',heilongjiangmap),
  setup() {
    const name = localStorage.getItem("ms_username");
    const role = name === "admin" ? "超级管理员" : "普通用户";
    let health_num = 0;
    const state =reactive(
        {
          chart:ref()
        }
    );
    const init=()=>{
    }
    // const data = reactive([
    //   {
    //     name: "2018/09/04",
    //     value: 1083,
    //   },
    //   {
    //     name: "2018/09/05",
    //     value: 941,
    //   },
    //   {
    //     name: "2018/09/06",
    //     value: 1139,
    //   },
    //   {
    //     name: "2018/09/07",
    //     value: 816,
    //   },
    //   {
    //     name: "2018/09/08",
    //     value: 327,
    //   },
    //   {
    //     name: "2018/09/09",
    //     value: 228,
    //   },
    //   {
    //     name: "2018/09/10",
    //     value: 1065,
    //   },
    // ]);
    const options = {
      type: "bar",
      title: {
        text: "最近一周各品类销售图",
      },
      xRorate: 25,
      labels: ["周一", "周二", "周三", "周四", "周五"],
      datasets: [
        {
          label: "家电",
          data: [234, 278, 270, 190, 230],
        },
        {
          label: "百货",
          data: [164, 178, 190, 135, 160],
        },
        {
          label: "食品",
          data: [144, 198, 150, 235, 120],
        },
      ],
    };
    const options2 = {
      type: "line",
      title: {
        text: "最近几个月各品类销售趋势图",
      },
      labels: ["6月", "7月", "8月", "9月", "10月"],
      datasets: [
        {
          label: "家电",
          data: [234, 278, 270, 190, 230],
        },
        {
          label: "百货",
          data: [164, 178, 150, 135, 160],
        },
        {
          label: "食品",
          data: [74, 118, 200, 235, 90],
        },
      ],
    };


   onMounted(()=>{
     init()
   })
    return {
      name,
      // data,
      options,
      options2,
      state,
      role,
      health_num,
    };
  },
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
  font-family: myfont;
}

.grid-con-icon {
  font-size: 50px;
  font-family: myfont;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}
#chart {
  margin: 0 auto;
}
.schart {
  width: 100%;
  height: 300px;
}
</style>
