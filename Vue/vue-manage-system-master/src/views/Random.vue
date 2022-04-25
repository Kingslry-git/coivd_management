<template>

  <div>
    <el-input
        v-model="district"
        placeholder="请输入抽检区域"
        class="input-with-select"
        style="width: 30%"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="地区" style="width: 110px">
          <el-option label="学校" value="1" />
          <el-option label="市/区/县" value="2" />
        </el-select>
      </template>
    </el-input>
    <el-input :style="[{'width':'20%','margin-left':'5px' }]"  v-model="size" placeholder="请输入抽检人数" />
    <el-button icon="el-icon-search" type="primary" style="margin-left: 5px" @click=" getData(district,size)"></el-button>




      <el-table :data="tableData"
                border style="width: 100%">
        <el-table-column prop="idCard" label="身份证号" width="220" align="center"/>
        <el-table-column prop="name" label="姓名" width="180" align="center" />
        <el-table-column prop="phone" label="电话号码" width="140" align="center" />
        <el-table-column prop="address" label="住址" align="center" />
      </el-table>

<!--  <div style="margin-top: 20px">-->
<!--    <el-button @click="toggleSelection([tableData[1], tableData[2]])"-->
<!--    >Toggle selection status of second and third rows</el-button-->
<!--    >-->
<!--    <el-button @click="toggleSelection()">Clear selection</el-button>-->
      <div style="text-align: right" >
        <el-switch v-model="value" />
        <hr class="my-4" />
        <el-pagination
            :hide-on-single-page="value"
            :total="30"
            layout="prev, pager, next"
        />
      </div>


  </div>

</template>

<script lang="ts" setup>
import { ref } from 'vue'
import type { ElTable } from 'element-plus'
import request from "../utils/request";
const select = ref('');




      const tableData = ref([

      ]);
      const value = ref(false);
      const district = ref('');
      const size = ref('');

    const getData = (district, size) => {
        request({
          url: "http://localhost:8080/patients/random?",
          method: "get",
          params: {
            address: district,
            size: size,
          }
        }).then(res => {
          console.log(res);
            tableData.value = res;
            }
        )
      };

      const print = () => {
        console.log(tableData);
      }
      const load = () => {
        let data = {
          idNumber: "",
          name: "",
          phone: "",
          address: "",
        }
        tableData.value.push(data);
      };

    // const log = () =>
    // {
    //   data = tableData;
    // }


</script>
