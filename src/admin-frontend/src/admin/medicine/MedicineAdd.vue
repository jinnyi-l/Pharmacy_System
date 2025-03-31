<template>
  <div class="medicineAdd">
    <el-page-header @back="goBack" content="添加药品"> </el-page-header>

    <div class="center">
      <div class="medicineAddForm">
        <el-form
          :model="medicineAddForm"
          :rules="rules"
          ref="medicineAddForm"
          label-width="100px"
          class="medicineAdd-form"
        >
          <el-form-item label="药品名称" prop="name">
            <el-input
              v-model="medicineAddForm.name"
              class="drug-name"
            ></el-input>
          </el-form-item>
          <el-form-item label="图片地址" prop="picture">
            

<el-upload  
      class="upload-demo"  
      action="http://localhost:9000/medicine/upload"  
      :on-change="handleChange"  
      :on-success="handleSuccess"  
      :on-error="handleError"  
      :before-upload="beforeUpload"  
      accept="image/png, image/jpeg, image/gif"  
      :file-list="fileList"  
      multiple  
    >  
      <el-button size="small" type="primary">点击上传</el-button>  
      <div slot="tip" class="el-upload__tip">只支持图片上传,多次上传只取最新图</div>  
    </el-upload>  

    <div v-if="imageUrl" class="image-preview">  
      <h3>图片预览：</h3>  
      <img :src="imageUrl" alt="preview" v-if="imageUrl"/>  
    </div>  
   

            
          </el-form-item>
          <el-form-item label="发布人" prop="uploadUser">
            <el-input
              v-model="medicineAddForm.uploadUser"
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

          <el-form-item label="价格（元）" prop="price">
            <el-input
              v-model="medicineAddForm.price"
              type="number"
              class="drug-price"
            ></el-input>
          </el-form-item>

          <el-form-item label="仓库剩余" prop="stock">
            <el-input
              v-model="medicineAddForm.stock"
              type="number"
              class="drug-stock"
            ></el-input>
          </el-form-item>

          <el-form-item label="药品描述" prop="description">
            <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 10 }"
              v-model="medicineAddForm.description"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm('medicineAddForm')"
              :disabled="disabled"
              >立即添加</el-button
            >
            <el-button @click="resetForm('medicineAddForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MedicineAdd",
  data() {
    return {
      imageUrl: '',
      value: [],
      // 接收传回的分类信息
      options: [],
      disabled: false,
      // 表单填写的信息
      medicineAddForm: {
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
  created() {
    this.getCategoryArr();
  },
  methods: {
     beforeUpload(file) {  
      const isImage = file.type.startsWith('image/');  
      const isLt2M = file.size / 1024 / 1024 < 2; // 限制2MB  

      if (!isImage) {  
        this.$message.error('上传文件只能是图片!');  
        return false; // 阻止上传  
      }  

      if (!isLt2M) {  
        this.$message.error('上传图片大小不能超过 2MB!');  
        return false; // 阻止上传  
      }  

      return true; // 允许上传 
    },  
    handleChange(file, fileList) {  
      this.fileList = fileList;  
    },  
    handleSuccess(response, file) {  
      this.$message.success('文件上传成功');  
      this.imageUrl = response.data.url; // 或者根据实际业务定义保存返回的 URL  
      this.medicineAddForm.picture = this.imageUrl
    },  
    handleError(err, file) {  
      this.$message.error('文件上传失败');  
    }  ,
  
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {

    },
    /* 提交表单 */
    submitForm(medicineAddForm) {
      this.$refs[medicineAddForm].validate((valid) => {
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
          medicine.name = this.medicineAddForm.name;
          medicine.picture = this.imageUrl;
          medicine.uploadUser = this.medicineAddForm.uploadUser;
          medicine.sort = this.value[0];
          medicine.secondSort = this.value[1];
          medicine.price = this.medicineAddForm.price;
          medicine.stock = this.medicineAddForm.stock;
          medicine.description = this.medicineAddForm.description;

          this.$axios.post("/medicine/add", medicine).then((res) => {
            _this.disabled = true;
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
                  let doWhat = "添加药品--药品名称：" + medicine.name+"，所属分类："+ this.options[medicine.sort].label;
                  formData.append("doWhat", doWhat);
                  _this.$axios.post("/log/add", formData);
                });
              // 提示信息
              _this.$alert(
                "添加成功!",
                "提示",
                {
                  confirmButtonText: "确定",
                  callback: (action) => {
                    _this.resetForm;
                    this.$router.push("/drug/list");
                  },
                }
              );
            } else {
              _this.$alert("操作失败！", "提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                  _this.resetForm;
                },
              });
            }
          });
        } else {
          console.log("信息填写有误!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.disabled = false;
      this.value = [];
      this.$refs[formName].resetFields();
    },
    /* 获取分类数组选项 */
    getCategoryArr() {
      this.$axios.post("/admin/category/arr").then((res) => {
        console.log(res);
        this.options = res.data.data.data;
      });
    },
    handleChange(value) {
      console.log("value："+value);
    },
    goBack() {
      console.log("go back");
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.medicineAdd >>> .el-page-header__content {
  color: #606266;
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
.upload-demo .el-upload__tip {  
  font-size: 12px;  
  color: #999;  
}  
.image-preview {  
  margin-top: 20px;  
}  
.image-preview img {  
  max-width: 300px;  
} 
@import "./style/MedicineAdd.css";
</style>
