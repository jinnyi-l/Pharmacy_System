<template>
  <div>
    <el-breadcrumb separator="/" class="carouselList-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">首页设置</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">轮播图</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="yjx-carouselList">
      <!-- 轮播图表格 -->
      <el-table :data="carouselData" style="width: 100%">
        <el-table-column label="序号" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="上传时间" width="250">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.gmtModified }}</span>
          </template>
        </el-table-column>
        <el-table-column label="图片" width="380">
          <template slot-scope="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 300px; height: 150px"
                :src="scope.row.path"
                :preview-src-list="srcList"
              >
              </el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="是否在首页展示" width="180">
          <template slot-scope="scope">
            <el-switch
              style="margin-left: 10px"
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              active-color="#409eff"
              inactive-color="#f0eeee"
              @change="isShowPicture(scope.row.id)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          label="上传者"
          width="180"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.adminName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-upload
              class="upload-list-inline"
              :action="
                $store.state.imgBaseUrl +
                'upload/carousel/picture/' +
                scope.row.id
              "
              :beforeUpload="uploadPicture"
            >
              <el-button size="small" type="primary">点击更换</el-button>
            </el-upload>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      carouselData: [],
      srcList: [],
    };
  },
  methods: {
    getCarouselData() {
      const _this = this;
      this.$axios.get("/query/carousels").then((res) => {
        console.log(res)
        _this.carouselData = res.data.data.data.records;
        for (const item of _this.carouselData) {
          _this.srcList.push(item.path);
        }
        // console.log(_this.carouselData);
        // console.log(_this.carouselData[0].path);
      });
    },
    /* 改变图片状态status */
    isShowPicture(pictureId) {
      const _this = this;
      this.$axios.get("/picture/isShow/" + pictureId).then((res) => {
        _this.$alert("操作成功", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {
            _this.getCarouselData();
          },
        });
      });
    },
    uploadPicture(file) {
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
              this.getCarouselData();
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
              this.getCarouselData();
            },
          }
        );
      }
    },
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3);
    },
  },
  created() {
    this.getCarouselData();
  },
};
</script>

<style scoped>
.carouselList-breadcrumb {
  margin: 0 0 15px 2px;
}
.yjx-carouselList {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 25px 30px 25px;
  min-width: 1200px;
  min-height: 500px;
}
</style>
