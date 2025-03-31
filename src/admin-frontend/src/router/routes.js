/* 后台相关的页面 */
const Home = () => import(/* webpackChunkName: "home" */"@/admin/Home")
const Login = () => import(/* webpackChunkName: "admin" */"@/admin/Login")
const MyInfoDetails = () => import(/* webpackChunkName: "admin" */"@/admin/MyInfoDetails")
// 后台管理 backstage
const Main = () => import(/* webpackChunkName: "admin" */"@/admin/backstage/Main")
const LogOperation = () => import(/* webpackChunkName: "admin" */"@/admin/backstage/LogOperation")
const test = () => import(/* webpackChunkName: "admin" */"@/admin/backstage/test")
// 药品管理 medicine
const MedicineList = () => import(/* webpackChunkName: "medicine-management" */"@/admin/medicine/MedicineList")
const MedicineEdit = () => import(/* webpackChunkName: "medicine-management" */"@/admin/medicine/MedicineEdit")
const MedicineAdd = () => import(/* webpackChunkName: "medicine-management" */"@/admin/medicine/MedicineAdd")
const MedicineCategory = () => import(/* webpackChunkName: "medicine-management" */"@/admin/medicine/MedicineCategory")
const SecondCategory = () => import(/* webpackChunkName: "medicine-management" */"@/admin/medicine/SecondCategory")
// 订单管理 order
const NewOrder = () => import(/* webpackChunkName: "order-management" */"@/admin/order/NewOrder")
const TransitOrder = () => import(/* webpackChunkName: "order-management" */"@/admin/order/TransitOrder")
const SureOrder = () => import(/* webpackChunkName: "order-management" */"@/admin/order/SureOrder")
const ComplainOrder = () => import(/* webpackChunkName: "order-management" */"@/admin/order/ComplaintOrder")
// 用户管理 user
const AdminList = () => import(/* webpackChunkName: "user-management" */"@/admin/user/AdminList")
const UserList = () => import(/* webpackChunkName: "user-management" */"@/admin/user/UserList")
const VipList = () => import(/* webpackChunkName: "user-management" */"@/admin/user/VipList")
const Test = () => import(/* webpackChunkName: "user-management" */"@/admin/Test")
// 首页设置 homemanage
const HealthTipsRecommend = () => import(/* webpackChunkName: "home-settings-page" */"@/admin/homemanage/HealthTipsRecommend")
const CarouselList = () => import(/* webpackChunkName: "home-settings-page" */"@/admin/homemanage/CarouselList")
const DrugRecommend = () => import(/* webpackChunkName: "home-settings-page" */"@/admin/homemanage/DrugRecommend")
// 资讯推送 information
const HealthTipsAdd = () => import(/* webpackChunkName: "information" */"@/admin/information/HealthTipsAdd")
const HealthTipsEdit = () => import(/* webpackChunkName: "information" */"@/admin/information/HealthTipsEdit")
const HealthTipsList = () => import(/* webpackChunkName: "information" */"@/admin/information/HealthTipsList")
const HealthArticle = () => import(/* webpackChunkName: "information" */"@/admin/information/HealthArticle")
// 错误页面，404，服务器休息等等页面
const ErrorPage = () => import(/* webpackChunkName: "admin" */"@/admin/error/ErrorPage")

const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
    path: '/home',
    redirect: '/index'
  }, {
    path: '/admin',
    redirect: '/index'
  }, {
    path: '/admin/login',
    name: Login,
    component: Login,
    meta: {
      title: '后台登录'
    }
  },
  {
    path: '/home',
    name: Home,
    component: Home,
    children: [
      /* 后台管理 */
      {
        path: '/index',
        name: Main,
        component: Main,
        meta: {
          title: '后台主页'
        }
      },
      {
        path: '/admin/test',
        name: test,
        component: test,
        meta: {
          title: '测试'
        }
      },
      {
        path: '/admin/log',
        name: LogOperation,
        component: LogOperation,
        meta: {
          title: '操作日志'
        }
      },
      {
        path: '/mine/info/:adminId',
        name: MyInfoDetails,
        component: MyInfoDetails,
        meta: {
          title: '我的信息',
          // requireAuth: true,
        }
      },
      /* 药品管理 */
      {
        path: '/drug/list',
        name: "MedicineList",
        component: MedicineList,
        meta: {
          title: '药品列表'
        }
      }, {
        path: '/drug/edit/:drugId',
        name: "MedicineEdit",
        component: MedicineEdit,
        meta: {
          title: '药品编辑'
        }
      }, {
        path: '/drug/add',
        name: "MedicineAdd",
        component: MedicineAdd,
        meta: {
          title: '药品添加'
        }
      }, {
        path: '/type/management',
        name: "MedicineCategory",
        component: MedicineCategory,
        meta: {
          title: '药品分类'
        }
      }, {
        path: '/type/management/:category/:categoryId',
        name: "SecondCategory",
        component: SecondCategory,
        meta: {
          title: '药品分类'
        }
      },
      /* 订单管理 */
      {
        path: '/orderForm/newOrder',
        name: NewOrder,
        component: NewOrder,
        meta: {
          title: '最新订单'
        }
      }, {
        path: '/orderForm/transitOrder',
        name: TransitOrder,
        component: TransitOrder,
        meta: {
          title: '运输中订单'
        }
      }, {
        path: '/orderForm/confirmedOrder',
        name: SureOrder,
        component: SureOrder,
        meta: {
          title: '已确认订单'
        }
      }, {
        path: '/orderForm/complaintOrder',
        name: ComplainOrder,
        component: ComplainOrder,
        meta: {
          title: '投诉订单'
        }
      },
      /* 用户管理 */
      {
        path: '/admin/list',
        name: "AdminList",
        component: AdminList,
        meta: {
          title: '管理员列表'
        }
      }, {
        path: '/user/list',
        name: "UserList",
        component: UserList,
        meta: {
          title: '用户列表'
        }
      }, {
        path: '/vip/list',
        name: VipList,
        component: VipList,
        meta: {
          title: '会员列表'
        }
      },
      /* 首页管理 */
      {
        path: '/home/health/tips/recommend',
        name: HealthTipsRecommend,
        component: HealthTipsRecommend,
        meta: {
          title: '健康资讯推荐'
        }
      },
       {
        path: '/drug/news/add',
        name: HealthTipsAdd,
        component: HealthTipsAdd,
        meta: {
          title: '添加资讯'
        }
      }, {
        path: '/drug/news/list',
        name: HealthTipsList,
        component: HealthTipsList,
        meta: {
          title: '资讯列表'
        }
      }, {
        path: '/article/details/:articleId',
        name: HealthArticle,
        component: HealthArticle,
        meta: {
          title: '文章详情'
        }
      }, {
        path: '/article/edit/:articleId',
        name: HealthTipsEdit,
        component: HealthTipsEdit,
        meta: {
          title: '文章编辑'
        }
      },
      {
        path: '/test',
        name: Test,
        component: Test
      }
    ]
  },
  {
    path: '/404',
    name: ErrorPage,
    component: ErrorPage,
    meta: {
      title: "找不到页面"
    }
  }
]

export default routes
