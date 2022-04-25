<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                    <i class="el-icon-lx-calendar"></i> 居民信息管理
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="formRef"  :model="form" label-width="110px" label-position="left" :rules="rules"
                         size="large" hide-required-asterisk="true" show-message="true">
                    <el-form-item label="姓名" :required="true" prop="name">
                        <el-input v-model="form.name"  autocomplete="off"  ></el-input>
                    </el-form-item>
                    <el-form-item label="身份证号" :required="true" prop="idCard">
                        <el-input v-model.number="form.idCard"  autocomplete="off" ></el-input>
                    </el-form-item>
                    <el-form-item label="性别" :required="true" prop="gender">
                        <el-radio v-model="form.gender" label="男">男</el-radio>
                        <el-radio v-model="form.gender" label="女">女</el-radio>
                    </el-form-item>
                    <el-form-item label="地址"  :required="true" prop="address">
                       <el-input v-model="form.address"  autocomplete="off" ></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model.number="form.phone"  autocomplete="off" ></el-input>
                    </el-form-item>
                    <el-form-item label="温度">
                        <el-input v-model.number="form.temp"  autocomplete="off" ></el-input>
                    </el-form-item>
                    <el-form-item label="最近核酸时间">
                        <span class="line"></span>
                        <el-date-picker
                            v-model="form.lastNat"
                            type="date"
                            placeholder="Select date"
                            format="YYYY/MM/DD"
                            value-format="YYYY-MM-DD"
                        />
                    </el-form-item>
                    <el-form-item label="第一针疫苗时间">
                        <el-date-picker
                            v-model="form.firstVaccine"
                            type="date"
                            placeholder="Select date"
                            format="YYYY/MM/DD"
                            value-format="YYYY-MM-DD"
                        />
                    </el-form-item>
                    <el-form-item label="第二针疫苗时间">
                        <el-date-picker
                            v-model="form.secVaccine"
                            type="date"
                            placeholder="Select date"
                            format="YYYY/MM/DD"
                            value-format="YYYY-MM-DD"
                        />
                    </el-form-item>
                        <el-form-item label="第三针疫苗时间">
                            <el-date-picker
                                v-model="form.thirdVaccine"
                                type="date"
                                placeholder="Select date"
                                format="YYYY/MM/DD"
                                value-format="YYYY-MM-DD"
                            />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">表单提交</el-button>
                            <el-button @click="onReset">重置表单</el-button>
                        </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { useRoute } from "vue-router";
import request from "../utils/request";
export default {
    name: "residentForm",
    setup() {
        const formRef = ref('');
        const route = useRoute();
        const form = reactive({
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
        const options = [
            {
                value: "黑龙江省",
                label: "黑龙江省",
                children: [
                    {
                        value: "哈尔滨市",
                        label: "哈尔滨市",

                    },
                    {
                      value: "齐齐哈尔市",
                      label: "齐齐哈尔市",

                    },{
                      value: "牡丹江市",
                      label: "牡丹江市",

                    },{
                      value: "佳木斯市",
                      label: "佳木斯市",

                    },{
                      value: "大庆市",
                      label: "大庆市",

                    },{
                      value: "鸡西市",
                      label: "鸡西市",

                    },{
                      value: "双鸭山市",
                      label: "双鸭山市",

                    },{
                      value: "伊春市",
                      label: "伊春市",

                    },{
                      value: "七台河市",
                      label: "七台河市",

                    },{
                      value: "鹤岗市",
                      label: "鹤岗市",

                    },{
                      value: "黑河市",
                      label: "黑河市",

                    },{
                      value: "绥化市",
                      label: "绥化市",
                    },
                ],
            },
        ];
        const rules = reactive( {
          idCard: [
            { required: true, message: "请输入身份证", trigger: "blur" },
          ],
          gender: [
            { required: true, message: "请选择性别", trigger: "blur" },
          ],
          name: [
            { required: true, message: "请输入名字", trigger: "blur" },
            { min: 2, max: 5, message: 'Length should be 2 to 5', trigger: 'blur' },
          ],
          address: [
            { required: true, message: "请输入地址", trigger: "blur" },
          ],

        });
        const getParams = () => {
          Object.keys(form).forEach((item) => {
            form[item] = route.params[item];
          });
        }
        getParams();
        // 提交
        const onSubmit = () => {
            // 表单校验
            formRef.value.validate((valid) => {
                if (valid) {
                   request.post(
                      'http://localhost:8080/patients/change',
                      {
                        name: form.name,
                        idCard: form.idCard,
                        gender: form.gender,
                        address: form.address,
                        phone: form.phone,
                        temp: form.temp,
                        lastNat: form.lastNat,
                        firstVaccine :form.firstVaccine,
                        secVaccine: form.secVaccine,
                        thirdVaccine: form.thirdVaccine,
                      }).then(res => {
                     console.log(res);
                      })
                     console.log(form);
                     ElMessage.success("提交成功！");
                }else {
                    return false;
                }
            });
        };
        // 重置
        const onReset = () => {
            formRef.value.resetFields();
        };
        return {
            options,
            rules,
            formRef,
            form,
            route,
            onSubmit,
            onReset,
            getParams,
        };
    },
};
</script>