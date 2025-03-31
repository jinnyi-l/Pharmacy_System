<template>
  <div class="myInfo">
    <el-page-header @back="goBack" content="个人资料"> </el-page-header>

    <div class="center">
      <div class="update">
        <el-button
          type="primary"
          size="small"
          @click="echoData()"
          v-if="this.$route.params.adminId == this.$store.state.userInfo.id"
          >修改资料</el-button
        >
      </div>
      <div class="updateItem">
        <el-dialog
          title="个人资料"
          :visible.sync="dialogFormVisible"
          width="38%"
        >
          <el-form :model="form">
            
            <el-form-item label="手机号" :label-width="formLabelWidth">
              <el-input
                v-model="form.phone"
                autocomplete="off"
                maxlength="11"
              ></el-input>
            </el-form-item>
            <el-form-item label="居住地" :label-width="formLabelWidth">
              <el-cascader
                :options="options"
                :props="{ expandTrigger: 'hover' }"
                clearable
                v-model="areaArr"
                @change="chooseArea()"
                style="width: 250px"
              >
              </el-cascader>
            </el-form-item>
            <el-form-item label="生日" :label-width="formLabelWidth">
              <el-date-picker
                v-model="dateValue"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
              <el-radio v-model="radio" label="1">男</el-radio>
              <el-radio v-model="radio" label="2">女</el-radio>
            </el-form-item>
            <el-form-item label="个性签名" :label-width="formLabelWidth">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 2 }"
                maxlength="30"
                placeholder="请输入内容"
                v-model="newSignature"
                show-word-limit
              >
              </el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="medium" @click="dialogFormVisible = false"
              >取 消</el-button
            >
            <el-button type="primary" size="medium" @click="confirmUpdate"
              >确定提交</el-button
            >
          </div>
        </el-dialog>
      </div>
      <div class="myInfoDesc">
        <el-descriptions class="margin-top" :column="1" border :labelStyle="LS">
          
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              用户名
            </template>
            {{ myInfo.username }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user-solid"></i>
              真实姓名
            </template>
            {{ myInfo.realName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{ myInfo.phone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              居住地
            </template>
            {{ showAddress(myInfo.address) }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              生日
            </template>
            {{ myInfo.birthday }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-star-off"></i>
              星座
            </template>
            {{ constellation }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i
                :class="
                  myInfo.gender === '男' ? 'el-icon-male' : 'el-icon-female'
                "
              ></i>
              性别
            </template>
            {{ myInfo.gender }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-promotion"></i>
              年龄
            </template>
            {{ getAge(myInfo.birthday) }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              个性签名
            </template>
            <el-tag
              size="medium"
              v-if="myInfo.signature != '' && myInfo.signature != null"
              >{{ myInfo.signature }}</el-tag
            >
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              邮箱地址
            </template>
            {{ myInfo.email }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              创建时间
            </template>
            {{ myInfo.gmtCreate }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script>
import { regionData, CodeToText, TextToCode } from "element-china-area-data";
export default {
  data() {
    return {
      dialogFormVisible: false,
      // 更改信息的表单
      form: {},
      formLabelWidth: "100px",
      // 选项
      options: regionData,
      // 选中的值用数组存储
      areaArr: [],
      // 生日绑定的日期
      dateValue: "",
      // 新的个性签名
      newSignature: "",
      myInfo: {},
      // 星座
      constellation: "",
      radio: "1",
      LS: {
        width: "300px",
      },
    };
  },
  methods: {
    goBack() {
      console.log("go back");
      this.$router.go(-1);
    },
    /* 获取个人信息 */
    handleMyInfo() {
      console.log(this.$route.params.adminId);
      this.$axios
        .get("/admin/info/" + this.$route.params.adminId, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          console.log(res);
          this.myInfo = res.data.data.data;
          this.form = res.data.data.data;
          let info = res.data.data.data;
          this.constellation = this.getConstellation(info.birthday);
          console.log(this.myInfo);
        });
    },
    // 回显个人信息
    echoData() {
      this.dialogFormVisible = true;
      console.log(this.form.address);
      this.showArea(this.form.address);
      if (this.form.gender === "女") {
        this.radio = "2";
      }
      this.dateValue = this.form.birthday;
      this.newSignature = this.form.signature;
    },
    confirmUpdate() {
      let admin = {
        id: this.myInfo.id,
        avatar: this.form.avatar,
        phone: this.form.phone,
        address: this.chooseArea(this.areaArr),
        birthday: this.dateValue,
        gender: this.radio === "1" ? "男" : "女",
        signature: this.newSignature,
      };
      console.log("提交===>", admin);
      this.$axios.post("/admin/update", admin).then((res) => {
        if (res.data.status) {
          this.$message({
            type: "success",
            message: res.data.message,
            showClose: true,
          });
          this.handleMyInfo();
          this.dialogFormVisible = false;
        } else {
          this.$message({
            type: "warning",
            message: res.data.message,
            showClose: true,
          });
        }
      });
    },
    // 区域地址改变
    chooseArea(areaArr) {
      if (areaArr != "" && areaArr != null) {
        // 把areaCode转换成地名并拼接起来
        let str = "";
        for (let i = 0; i < areaArr.length; i++) {
          str += CodeToText[areaArr[i]] + "/";
        }
        console.log(str.substring(0, str.length - 1));
        return str.substring(0, str.length - 1);
      }
    },
    // 去除地址的 / ，拼接
    showAddress(address) {
      if (address != "" && address != null) {
        let arr = address.split("/");
        let str = "";
        for (const item of arr) {
          str += item;
        }
        return str;
      }
    },
    /* 更改资料：前端展示从后端查到的地址，需要一个数组 */
    showArea(address) {
      if (address != null && address != "") {
        let addressArr = address.split("/");
        let areaCodeArr = [];
        if (addressArr.length == 2) {
          areaCodeArr.push(TextToCode[addressArr[0]].code);
          areaCodeArr.push(TextToCode[addressArr[0]][addressArr[1]].code);
        } else if (addressArr.length == 3) {
          areaCodeArr.push(TextToCode[addressArr[0]].code);
          areaCodeArr.push(TextToCode[addressArr[0]][addressArr[1]].code);
          areaCodeArr.push(
            TextToCode[addressArr[0]][addressArr[1]][addressArr[2]].code
          );
        } else {
          return null;
        }
        this.areaArr = areaCodeArr;
      } else {
        return null;
      }
    },
    /* 上传头像 */
    updateAvatar(file) {
      let fileSize = file.size / 1024 / 1024;
      if (fileSize > 1) {
        this.$alert(
          "&emsp;&emsp;<span style='color:red'><strong>上传失败！！！</strong></span><br/>&emsp;&emsp;您上传的图片的大小为<span style='color:red'>" +
            Math.floor(fileSize * 100) / 100 +
            "MB</span>，不符合”<span style='color:blue'>图片的大小不能超过1MB</span>“的要求，请您重新选择合适的图片。",
          "提示",
          {
            dangerouslyUseHTMLString: true,
            type: "",
            callback: () => {
              // this.getCarouselData();
            },
          }
        );
        return false;
      } else {
        this.$alert(
          "<span style='color:green'>&emsp;&emsp;图片更换成功！</span>",
          "提示",
          {
            dangerouslyUseHTMLString: true,
            callback: () => {
              this.handleMyInfo();
              this.dialogFormVisible = false;
              this.$router.go(0);
            },
          }
        );
      }
    },
    /* 计算年龄 */
    getAge(birthday) {
      let date = new Date();
      // 得到当前时间
      let yearNow = date.getFullYear();
      let monthNow = date.getMonth() + 1;
      let dayNow = date.getDate();
      // 把生日分成年月日
      // let birthday = this.$store.getters.getUser.birthday;
      if (birthday != "" && birthday != null) {
        let str = birthday.split("-");

        let yearBirth = str[0];
        let monthBirth = str[1];
        let dayBirth = str[2];
        if (monthNow < monthBirth) {
          let age = yearNow - yearBirth - 1;
          return age;
        } else if (monthNow > monthBirth) {
          let age = yearNow - yearBirth;
          return age;
        } else {
          if (dayNow < dayBirth) {
            let age = yearNow - yearBirth - 1;
            return age;
          } else {
            let age = yearNow - yearBirth;
            return age;
          }
        }
      }
    },
    /* 判断星座 */
    getConstellation(birthday) {
      // 把生日分成年月日
      // let birthday = this.$store.getters.getUser.birthday;
      if (null != birthday && birthday != "") {
        let str = birthday.split("-");

        let monthBirth = str[1];
        let dayBirth = str[2];

        if (monthBirth == 1) {
          return dayBirth < 21 ? "摩羯座" : "水瓶座";
        } else if (monthBirth == 2) {
          return dayBirth < 20 ? "水瓶座" : "双鱼座";
        } else if (monthBirth == 3) {
          return dayBirth < 21 ? "双鱼座" : "白羊座";
        } else if (monthBirth == 4) {
          return dayBirth < 21 ? "白羊座" : "金牛座";
        } else if (monthBirth == 5) {
          return dayBirth < 22 ? "金牛座" : "双子座";
        } else if (monthBirth == 6) {
          return dayBirth < 22 ? "双子座" : "巨蟹座";
        } else if (monthBirth == 7) {
          return dayBirth < 23 ? "巨蟹座" : "狮子座";
        } else if (monthBirth == 8) {
          return dayBirth < 24 ? "狮子座" : "处女座";
        } else if (monthBirth == 9) {
          return dayBirth < 24 ? "处女座" : "天秤座";
        } else if (monthBirth == 10) {
          return dayBirth < 24 ? "天秤座" : "天蝎座";
        } else if (monthBirth == 11) {
          return dayBirth < 23 ? "天蝎座" : "射手座";
        } else if (monthBirth == 12) {
          return dayBirth < 22 ? "射手座" : "摩羯座";
        }
      }
    },
  },
  created() {
    this.handleMyInfo();
  },
};
</script>

<style scoped>
.myInfo >>> .el-page-header__content {
  color: #606266;
}

.myInfo .center {
  margin: 15px 0;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 50px 38px;
  min-width: 1200px;
}

.myInfo .center .update {
  text-align: right;
  margin-bottom: 16px;
}

.myInfo .center .updateItem .upload-list-inline {
  display: inline-block;
  margin-left: 28px;
}
</style>
