<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 居民表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
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
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-input v-model="query.origin" placeholder="感染来源" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="idCard" label="身份证" width="165" align="center"></el-table-column>
                <el-table-column prop="name" lab3el="姓名" width="80" align="center"></el-table-column>
                <el-table-column prop="gender" label="性别" width="50" align="center"></el-table-column>
                <el-table-column prop="phone" label="电话" align="center"></el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column  label="体温" align="center">
                    <template #default="scope">{{ scope.row.temp }}℃</template>
                </el-table-column>

                <el-table-column prop="symptom" label="状态"></el-table-column>

                <el-table-column label="操作" width="250" align="center">
                    <template #default="scope">
                        <el-button-group>
                            <el-button type="warning" round plain
                                @click="handleQuarantine(scope.$index, scope.row)">隔离
                            </el-button>
                            <el-button type="warning"  plain
                                       @click="handleInfectEdit(scope.$index, scope.row)">感染
                            </el-button>
                            <el-button type="warning" round plain
                                @click="handleDisisolation(scope.$index, scope.row)">解除
                            </el-button>
                        </el-button-group>
                            <el-button type="info" round plain size="large"
                                @click="handleMoreInformation(scope.$index, scope.row)">查看详情
                            </el-button>
                            <el-button type="info" round plain size="large"
                                @click="goToOtherPage(scope.row)">信息更新
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

        <!-- 详细信息弹出框 -->
        <el-dialog title="详细信息" v-model="editVisible" width="30%">
            <el-descriptions
                title="居民详细信息"
                direction="vertical"
                :column="4"
                :size="size"
                border
            >
                <el-descriptions-item label="名字">{{form.name}}</el-descriptions-item>
                <el-descriptions-item label="历经风险区">{{form.origin}}</el-descriptions-item>
                <el-descriptions-item label="更新时间" :span="2">{{form.admissionDate}}</el-descriptions-item>
                <el-descriptions-item label="最后一次核酸时间" :span="2">{{form.lastNat}}</el-descriptions-item>
                <el-descriptions-item label="第一针疫苗接种时间" :span="2">{{form.firstVaccine}}</el-descriptions-item>
                <el-descriptions-item label="第二针疫苗接种时间" :span="2">{{form.secVaccine}}</el-descriptions-item>
                <el-descriptions-item label="第三针疫苗接种时间" :span="2">{{form.thirdVaccine}}</el-descriptions-item>
                <el-descriptions-item label="Remarks">{{form.remarks}}</el-descriptions-item>
            </el-descriptions>
        </el-dialog>
        <!-- 隔离弹出框 -->
        <el-dialog title="隔离信息" v-model="quarantineVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名: ">
                    {{form.name}}
                </el-form-item>
                <el-form-item label="风险区">
                    <el-input v-model="form.origin"></el-input>
                </el-form-item>
                <el-form-item label="隔离时间">
                    <el-radio-group v-model="form.chosen">
                        <el-radio label="七天" />
                        <el-radio label="十四天" />
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="quarantineVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveQuarantineEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 感染弹出框 -->
        <el-dialog title="感染信息" v-model="infectVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名: ">
                    {{form.name}}
                </el-form-item>
                <el-form-item label="风险区">
                    <el-input v-model="form.origin"></el-input>
                </el-form-item>
                <el-form-item label="症状">
                    <el-radio-group v-model="form.symptom">
                        <el-radio label="无症状" />
                        <el-radio label="轻症" />
                        <el-radio label="重症" />
                    </el-radio-group>
                </el-form-item>
              </el-form>
              <template #footer>
                      <span class="dialog-footer">
                          <el-button @click="infectVisible = false">取 消</el-button>
                          <el-button type="primary" @click="saveInfectEdit">确 定</el-button>
                      </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from "../utils/request";
import { useRouter } from "vue-router";

export default {
    name: "患者管理",
    setup() {
        const query = reactive({
          name: "",
          idCard: "",
          phone: "",
          gender: "",
          address: "",
          temp: 36,
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
        const router = useRouter();
        const getData = () => {
            request({
                url: "http://localhost:8080/patients/page?",
                method: "get",
                params: {
                    address: query.address,
                    name: query.name,
                    origin: query.origin,
                    state: "健康",
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
        const handleDisisolation = (index,row) => {
            ElMessageBox.confirm("确定将该患者解除隔离吗？", "提示", {
              type: "warning",
            }).then(() => {
                row.remarks += "于"+row.admissionDate+"解除隔离";
                ElMessage.success("更改成功");
                request.post(
                    'http://localhost:8080/patients/change',
                    {
                        idCard: row.idCard,
                        state:  "健康",
                        symptom: "健康",
                        remarks: row.remarks,
                        temp: row.temp,
                    }).then(res => {
                  console.log(res);
                })
            }).catch(() => {});
            tableData.value.splice(index, 1);
            getData();
            getData();
        };
        const goToOtherPage = (row) => {
            router.push({
              name: "form",
              params: row,
            })
        };
        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        const quarantineVisible = ref(false);
        const infectVisible = ref(false);
        let form = reactive({
            name: "",
            idCard: "",
            phone: "",
            gender: "",
            address: "",
            temp: 36,
            lastNat: "",
            firstVaccine: "",
            secVaccine: "",
            thirdVaccine: "",
            state:  "",
            admissionDate: "",
            origin: "",
            symptom: "",
            remarks: "",
            chosen: "",
        });
        let idx = -1;
        const handleMoreInformation = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
              form[item] = row[item];
            });
            editVisible.value = true;
        };
        const handleQuarantine = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            quarantineVisible.value = true;
        };
        const handleInfectEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            infectVisible.value = true;
        };
        const saveQuarantineEdit = () => {
            form.remarks += "自"+form.admissionDate+"隔离"+form.chosen+"\n";
            request.post(
                'http://localhost:8080/patients/change',
                {
                  idCard: form.idCard,
                  state: "健康",
                  origin: form.origin,
                  symptom: "例行隔离",
                  remarks: form.remarks,
                  temp: form.temp,
                }
            ).then(res => {
              console.log(res);
            })
            quarantineVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
            getData();
        };
        const saveInfectEdit = () => {
            form.remarks += "自"+form.admissionDate+"感染，为"+form.symptom+"\n";
            request.post(
                'http://localhost:8080/patients/change',
                {
                    idCard: form.idCard,
                    state: "感染",
                    origin: form.origin,
                    symptom:  form.symptom,
                    remarks: form.remarks,
                    temp: form.temp,
                }
            ).then(res => {
                console.log(res);
            })
            infectVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
            getData();
        };
        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            quarantineVisible,
            infectVisible,
            form,
            handleSearch,
            handlePageChange,
            handleMoreInformation,
            goToOtherPage,
            handleQuarantine,
            handleInfectEdit,
            saveQuarantineEdit,
            saveInfectEdit,
            handleDisisolation,
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

.mr10 {
    margin-right: 10px;
}

</style>
