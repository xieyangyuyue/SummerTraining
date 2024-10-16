<template>
    <div>
        <h1 class="home-title">个人中心</h1>
        <el-descriptions :title="'欢迎 ' + user.name" :column="2" size="medium" border>
            <el-descriptions-item>
                <template #label>
                    <el-icon>
                        <Avatar />
                    </el-icon>
                    账号
                </template>
                {{ user.name }}
            </el-descriptions-item>

            <el-descriptions-item>
                <template #label>
                    <el-icon>
                        <phone />
                    </el-icon>
                    电话
                </template>
                {{ user.phone }}
            </el-descriptions-item>

            <el-descriptions-item>
                <template #label>
                    <el-icon>
                        <component :is="sexIcon" />
                    </el-icon>
                    性别
                </template>
                <el-tag :type="user.sex === 0 ? 'primary' : 'danger'" disable-transition>
                    {{ user.sex === 0 ? '男' : '女' }}
                </el-tag>
            </el-descriptions-item>

            <el-descriptions-item>
                <template #label>
                    <el-icon>
                        <tickets />
                    </el-icon>
                    角色
                </template>
                <el-tag type="success" disable-transition>
                    {{ roleText }}
                </el-tag>
            </el-descriptions-item>
        </el-descriptions>
        <div class="home-time">
            <DateUtils />
        </div>

    </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import DateUtils from './DateUtils.vue';
import { Male, Female } from '@element-plus/icons-vue';

export default {
    name: "Home",
    components: { DateUtils, Male, Female },
    setup() {
        const user = ref({});

        const roleText = computed(() => {
            switch (user.value.roleId) {
                case 0: return "超级管理员";
                case 1: return "管理员";
                default: return "用户";
            }
        });

        const sexIcon = computed(() => {
            return user.value.sex === 0 ? Female : Male;
        });
        onMounted(() => {
            user.value = JSON.parse(sessionStorage.getItem('LoginUser'));
        });

        return {
            user,
            roleText,
            sexIcon,
            // customIcon
        };
    }
};
</script>

<style scoped>
.home-title {
    margin: 20px 0;
    text-align: center;
}

.home-time {
    margin: 20px 0;
    text-align: center;
    font-size: 100px;
    /* Increased font size for time display */
    font-weight: bold;
    /* Optional: Bold text for emphasis */
    color: #555;
    /* Optional: Color adjustment */
}
</style>