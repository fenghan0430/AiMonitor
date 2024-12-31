// 饼图与柱状图初始化
document.addEventListener("DOMContentLoaded", () => {
    const pieCtx = document.getElementById("pieChart").getContext("2d");
    const barCtx = document.getElementById("barChart").getContext("2d");
    const statusIcon = document.getElementById("status-icon");
    const onlineStatusText = document.getElementById("online-status-text");
    const lastUpdateTime = document.getElementById("last-update-time");
    
    // 示例数据
    new Chart(pieCtx, {
        type: "pie",
        data: {
            labels: ["正常", "错误"],
            datasets: [{
                data: [80, 20],
                backgroundColor: ["#10B981", "#F43F5E"],
            }]
        }
    });

    new Chart(barCtx, {
        type: "bar",
        data: {
            labels: ["1月", "2月", "3月", "4月", "5月"],
            datasets: [{
                label: "正常天数",
                data: [25, 22, 28, 26, 30],
                backgroundColor: "#3B82F6",
            }]
        }
    });

    // 示例逻辑：动态显示在线状态
    const isOnline = true; // 模拟在线状态，切换为 false 测试错误状态

    if (isOnline) {
        statusIcon.className = "status-icon success";
        statusIcon.innerHTML = "&#10004;"; // 勾号
        onlineStatusText.textContent = "所有系统运行正常";
    } else {
        statusIcon.className = "status-icon error";
        statusIcon.innerHTML = "&#10060;"; // 使用红色叉号符号
        onlineStatusText.textContent = "系统出现故障";
    }

    // 更新最近更新时间
    const now = new Date();
    lastUpdateTime.textContent = `最近更新：${now.getFullYear()}-${String(
        now.getMonth() + 1
    ).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")} ${String(
        now.getHours()
    ).padStart(2, "0")}:${String(now.getMinutes()).padStart(2, "0")}`;
});

// 分页、刷新数据逻辑示例
document.getElementById("refreshData").addEventListener("click", () => {
    alert("数据已刷新！");
});

document.getElementById("addData").addEventListener("click", () => {
    alert("手动添加数据功能未实现！");
});
