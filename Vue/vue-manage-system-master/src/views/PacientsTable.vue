<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 患者表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
                    <el-option key="0" label="所有城市" value=""></el-option>
                    <el-option key="1" label="哈尔滨市" value="哈尔滨市"></el-option>
                    <el-option key="2" label="齐齐哈尔市" value=“齐齐哈尔市”></el-option>
                    <el-option key="3" label="牡丹江市" value=“牡丹江市”></el-option>
                    <el-option key="4" label="佳木斯市" value=“佳木斯市”></el-option>
                    <el-option key="5" label="大庆市" value=“大庆市”></el-option>
                    <el-option key="6" label="鸡西市" value=“鸡西市”></el-option>
                    <el-option key="7" label="双鸭山市" value=“双鸭山市”></el-option>
                    <el-option key="8" label="伊春市" value=“伊春市”></el-option>
                    <el-option key="9" label="七台河市" value=“七台河市”></el-option>
                    <el-option key="10" label="鹤岗市" value=“鹤岗市”></el-option>
                    <el-option key="11" label="黑河市" value=“黑河市”></el-option>
                    <el-option key="12" label="绥化市" value=“绥化市”></el-option>
                </el-select>
                <el-input v-model="query.name" placeholder="姓名" class="handle-input mr10"></el-input>
                <el-input v-model="query.origin" placeholder="感染来源" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="idCard" label="身份证" width="165" align="center"></el-table-column>
                <el-table-column prop="name" label="姓名" width="80" align="center"></el-table-column>
                <el-table-column prop="gender" label="性别" width="50" align="center"></el-table-column>
                <el-table-column prop="origin" label="感染来源" align="center"></el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column label="症状" align="center">
                    <template #default="scope">
                        <el-tag :type="
                                scope.row.symptom === '确诊'
                                    ? '确诊'
                                    : scope.row.symptom === '无症状'
                                    ? '无症状'
                                    : ''
                            ">{{ scope.row.symptom }}</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="体温" align="center" >
                    <template #default="scope">{{ scope.row.temp }}℃</template>
                </el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button-group>
                            <el-button type="warning" round plain
                                @click="handleCure(scope.$index, scope.row)">治愈
                            </el-button>
                            <el-button type="warning" round plain
                                @click="handleDeath(scope.$index, scope.row)">死亡
                            </el-button>
                        </el-button-group>
                        <el-button type="info" round plain
                            @click="handleMoreInformation(scope.$index, scope.row)">查看详情
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="详细信息" v-model="editVisible" width="30%">
            <el-descriptions
                title="患者详细信息"
                direction="vertical"
                :column="4"
                :size="size"
                border
            >
                <el-descriptions-item label="名字">{{form.name}}</el-descriptions-item>
                <el-descriptions-item label="手机号码">{{form.phone}}</el-descriptions-item>
                <el-descriptions-item label="更新日期" :span="2">{{form.admissionDate}}</el-descriptions-item>
                <el-descriptions-item label="最后一次核酸时间" :span="2">{{form.lastNat}}</el-descriptions-item>
                <el-descriptions-item label="第一针疫苗接种时间" :span="2">{{form.firstVaccine}}</el-descriptions-item>
                <el-descriptions-item label="第二针疫苗接种时间" :span="2">{{form.secVaccine}}</el-descriptions-item>
                <el-descriptions-item label="第三针疫苗接种时间" :span="2">{{form.thirdVaccine}}</el-descriptions-item>
                <el-descriptions-item label="Remarks">{{form.remarks}}</el-descriptions-item>
            </el-descriptions>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from "../utils/request";

export default {
    name: "患者管理",
    setup() {
        const query = reactive({
          name: "",
          idCard: "",
          phone: "",
          gender: "",
          address: "",
          temp: 0,
          lastNat: "",
          firstVaccine: "",
          secVaccine: "",
          thirdVaccine: "",
          state:  "",
          admissionDate: "",
          origin: "",
          symptom: "",
          remarks: "",
          pageNum: 1,
          pageSize: 10,

        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        const getData = () => {
            request({
                url: "http://localhost:8080/patients/page?",
                method: "get",
                params: {
                    address: query.address,
                    name: query.name,
                    origin: query.origin,
                    state: "感染",
                    pageNum: query.pageNum,
                    pageSize: query.pageSize,
                }
            }).then(res => {
              console.log(res);
              tableData.value = res.records;
              pageTotal.value = res.records.length;
            })
        };
        getData();
        //治愈操作
        const handleCure = (index,row) => {
            ElMessageBox.confirm("确定该患者已治愈吗？", "提示", {
                type: "warning",
            }).then(() => {
                row.remarks = "于"+row.admissionDate+"治愈";
                ElMessage.success("更改成功");
                request.post(
                    'http://localhost:8080/patients/change',
                    {
                        idCard: row.idCard,
                        temp: row.temp,
                        state:  "健康",
                        origin: "",
                        symptom: "",
                        remarks: row.remarks,
                        }).then(res => {
                        console.log(res);
                    })
                }).catch(() => {});
            tableData.value.splice(index, 1);
            getData();
        }
        // 死亡操作
        const handleDeath = (index,row) => {
            ElMessageBox.confirm("确定该患者已死亡吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    row.remarks += row.remarks+"于"+row.admissionDate+"死亡";
                    ElMessage.success("更改成功");
                    request.post(
                        'http://localhost:8080/patients/change',
                        {
                            idCard: row.idCard,
                            state: "死亡",
                            remarks: row.remarks,
                        }
                    ).then(res => {
                        console.log(res);
                    })
                })
                .catch(() => {});
            tableData.value.splice(index, 1);
            getData();
        };
        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getData();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };
        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            phone: "",
            temp: 0,
            lastNat: "",
            firstVaccine: "",
            secVaccine: "",
            thirdVaccine: "",
            remarks: "",
            admissionDate: "",
        });
        let idx = -1;
        const handleMoreInformation = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
              form[item] = row[item];
            });
            editVisible.value = true;
      };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
        };

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handlePageChange,
            handleDeath,
            handleCure,
            handleMoreInformation,
            saveEdit,
        };
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-origin {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
