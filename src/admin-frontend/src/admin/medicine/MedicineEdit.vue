<template>
  <div class="medicineEdit">
    <el-page-header @back="goBack()" content="药品编辑"> </el-page-header>

    <div class="center">
      <div class="edit-form">
        <el-form
          :model="medicineEditForm"
          :rules="rules"
          ref="medicineEditForm"
          label-width="100px"
          class="medicineEdit-form"
        >
          <el-form-item label="药品名称" prop="name">
            <el-input
              v-model="medicineEditForm.name"
              class="drug-name"
            ></el-input>
          </el-form-item>
          <el-form-item label="图片地址" prop="picture">
            <el-input
            placeholder="请输入药品图片url"
              v-model="imageUrl"
              class="drug-name"
            ></el-input>
            
          </el-form-item>
          <el-form-item label="发布人" prop="uploadUser">
            <el-input
              v-model="medicineEditForm.uploadUser"
              class="drug-uploadUser"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属分类" prop="sort">
            <el-cascader
              v-model="value"
              :options="options"
              :props="{ expandTrigger: 'hover' }"
              @change="handleChange"
            ></el-cascader>
          </el-form-item>

          <el-form-item label="价格 ( 元 )" prop="price">
            <el-input
              v-model="medicineEditForm.price"
              type="number"
              class="drug-price"
            ></el-input>
          </el-form-item>

          <el-form-item label="仓库剩余" prop="stock">
            <el-input
              v-model="medicineEditForm.stock"
              type="number"
              class="drug-stock"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="药品描述"
            prop="description"
            class="drug-description"
          >
            <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 10 }"
              v-model="medicineEditForm.description"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('medicineEditForm')"
              >立即更改</el-button
            >
<!--            <el-button @click="resetForm('medicineEditForm')">重置</el-button>-->
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MedicineEdit",
  data() {
    return {
      imageUrl: '',
      value: [],
      // 接收传回的分类信息
      options: [],
      // 表单填写的信息
      medicineEditForm: {
        name: "",
        picture: "",
        uploadUser: "",
        price: "",
        stock: "",
        description: "",
      },
      // 校验规则
      rules: {
        name: [
          {
            required: true,
            message: "请填写药品名称！",
            trigger: "blur",
          },
          {
            min: 1,
            max: 50,
            message: "长度在 1 到 50 个字符",
            trigger: "blur",
          },
        ],
        // picture: [
        //   {
        //     required: true,
        //     message: "请填写上传图片的地址！",
        //     trigger: "blur",
        //   },
        // ],
        uploadUser: [
          { required: true, message: "请填写上传者的名字！", trigger: "blur" },
        ],
        price: [
          {
            required: true,
            message: "请填写价格！",
            trigger: "blur",
          },
        ],
        stock: [
          {
            required: true,
            message: "请填写药品库存数量！",
            trigger: "blur",
          },
        ],
        description: [
          {
            required: true,
            message: "请填写药品的作用描述！",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {

    },
    /* 返回到列表 */
    goBack() {
      this.$router.push("/drug/list");
    },
    /* 根据药品的id回显信息 */
    getInfoById() {
      const drugId = this.$route.params.drugId;
      const _this = this;
      this.$axios.get("/medicine/get/info/" + drugId).then((res) => {
        console.log("回显数据=>", res);
        _this.medicineEditForm.name = res.data.data.data.name;
        _this.medicineEditForm.picture = res.data.data.data.picture;
        this.imageUrl=res.data.data.data.picture;
        _this.medicineEditForm.uploadUser = res.data.data.data.uploadUser;
        // _this.medicineEditForm.sort = res.data.data.data.sort;
        _this.medicineEditForm.price = res.data.data.data.price;
        _this.medicineEditForm.stock = res.data.data.data.stock;
        _this.medicineEditForm.description = res.data.data.data.description;
        this.value.push(res.data.data.data.sort);
        this.value.push(res.data.data.data.secondSort);
      });
    },
    /* 获取分类数组选项 */
    getCategoryArr() {
      this.$axios.post("/admin/category/arr").then((res) => {
        console.log(res);
        this.options = res.data.data.data;
      });
    },
    /* 提交表单 */
    submitForm(medicineEditForm) {
      const drugId = this.$route.params.drugId;
      this.$refs[medicineEditForm].validate((valid) => {
        if (valid) {
          const _this = this;
          let medicine = {
            name: "",
            picture: "",
            uploadUser: "",
            sort: "",
            secondSort: "",
            price: "",
            stock: "",
            description: "",
          };
          console.log(this.value);
          console.log(this.value[0]);
          console.log(this.value[1]);
          medicine.name = this.medicineEditForm.name;
          medicine.picture = this.imageUrl;
          medicine.uploadUser = this.medicineEditForm.uploadUser;
          medicine.sort = this.value[0];
          medicine.secondSort = this.value[1];
          medicine.price = this.medicineEditForm.price;
          medicine.stock = this.medicineEditForm.stock;
          medicine.description = this.medicineEditForm.description;

          console.log(medicine);
          console.log(this.imageUrl);
          this.$axios
            .post("/update/medicine/info/" + drugId, medicine)
            .then((res) => {
              this.getInfoById();
              if (res.data.data.responseBody) {
                // 记录日志
                _this.$axios
                  .get("/admin/info/" + _this.$store.getters.getUser.id, {
                    headers: {
                      Authorization: localStorage.getItem("token"),
                    },
                  })
                  .then((res) => {
                    let user = res.data.data.data;
                    let formData = new FormData();
                    formData.append(
                      "auth",
                      user.auth == 1 ? "超级管理员" : "管理员"
                    );
                    formData.append("admin", user.realName);
                    let doWhat = "修改药品--药品名称：" + medicine.name;
                    formData.append("doWhat", doWhat);
                    _this.$axios.post("/log/add", formData);
                  });
                // 提示信息
                _this.$alert("修改药品成功！", "提示", {
                  confirmButtonText: "确定",
                  callback: (action) => {
                    // 返回药品列表页
                    _this.$router.push("/drug/list");
                  },
                });
              } else {
                _this.$alert("操作失败！", "提示", {
                  confirmButtonText: "确定",
                  callback: (action) => {},
                });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    /* 重置表单信息 */
    resetForm(medicineEditForm) {
      this.$refs[medicineEditForm].resetFields();
    },
    // /*在点击分类选项后，发送请求获取分类信息，传给options */
    // getSort() {
    //   const _this = this;
    //   this.$axios.get("/medicine/sortList").then((res) => {
    //     _this.options = res.data.data.sortData;
    //   });
    // },
    handleChange(value) {
      console.log(value);
    },
  },
  created() {
    this.getInfoById();
    this.getCategoryArr();
  },
};
</script>

<style scoped>
.medicineEdit .center {
  margin: 15px 0;
  padding: 20px 30px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
  min-height: 500px;
  color: #000000a6;
}
.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader >>> .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.medicineEdit .edit-form {
  width: 600px;
  margin: auto;
}
</style>

