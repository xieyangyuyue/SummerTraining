<template>
    <!-- background-color 	菜单的背景颜色 (十六进制格式) (已废弃，请在样式类中使用 --el-menu-bg-color	
     text-color deprecated	菜单的文字颜色 (十六进制格式) 
    active-text-color deprecated	活动菜单项的文本颜色（十六进制格式）
    default-active	页面加载时默认激活菜单的 index
    collapse	是否水平折叠收起菜单（仅在 mode 为 vertical 时可用）boolen
    :collapse  :冒号代表变量 等于好后赋值  一般存入data return中
     collapse-transition	是否开启折叠动画	boolean-->

    <!-- 通过 el-menu-item-group 组件可以实现菜单进行分组，
      分组名可以通过 title 属性直接设定，也可以通过具名 slot 来设定。 -->
    <el-menu active-text-color="#ffd04b" background-color="#545c64" text-color="#fff" style="height: 100vh;"
        default-active="/Home" :collapse="isCollapse" :collapse-transition="false" router>
        <!-- 左侧Menu 菜单 -->
        <!-- index 	唯一标志 -->
        <el-menu-item index="/Home">
            <el-icon>
                <HomeFilled />
            </el-icon>
            <span slot="title">首页</span>
        </el-menu-item>

        <el-menu-item :index="'/' + item.menuclick" v-for="(item, i) in menu" :key="i">
            <el-icon>
                <Flag />
            </el-icon>
            <span slot="title">{{ item.menuname }}</span>
        </el-menu-item>

    </el-menu>
</template>

<script>


export default {
    name: "Aside",
    data() {
        return {
            // menu: [
            //   {
            //     menuClick: 'Admin',
            //     menuName: '管理员管理',
            //     menuIcon: 'Opportunity'
            //   },
            //   {
            //     menuClick: 'User',
            //     menuName: '用户管理',
            //     menuIcon: 'Flag'
            //   }
            // ]
        }
    },
    computed: {
        "menu": {
            get() {
                return this.$store.state.menu
            }
        }
    },

    props: {
        isCollapse: Boolean
    }

}
</script>
<style></style>