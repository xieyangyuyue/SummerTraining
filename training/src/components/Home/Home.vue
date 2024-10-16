<template>
    <div>
        <h1 class="home-title">个人中心</h1>
        <el-button type="primary" @click="navigateToEditProfile" class="update-button">修改个人信息</el-button>
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
                        <Phone />
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
                        <Tickets />
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
import { useRouter } from 'vue-router';
import DateUtils from '../DateUtils.vue';
import { Avatar, Phone, Tickets } from '@element-plus/icons-vue';

export default {
    name: 'Home',
    components: { DateUtils, Avatar, Phone, Tickets },
    setup() {
        const router = useRouter();
        const user = ref({});

        const roleText = computed(() => {
            switch (user.value.roleId) {
                case 0: return "超级管理员";
                case 1: return "管理员";
                default: return "用户";
            }
        });

        const sexIcon = computed(() => {
            return user.value.sex === 0 ? 'Male' : 'Female';
        });

        onMounted(() => {
            user.value = JSON.parse(sessionStorage.getItem('LoginUser'));
        });

        const navigateToEditProfile = () => {
            router.push('/edit-profile');
        };

        return {
            user,
            roleText,
            sexIcon,
            navigateToEditProfile
        };
    }
};
</script>

<style scoped>
.home-title {
    margin: 20px 0;
    text-align: center;
}

.update-button {
    position: absolute;
    top: 20px;
    right: 20px;
}

.home-time {
    margin: 20px 0;
    text-align: center;
}
</style>
