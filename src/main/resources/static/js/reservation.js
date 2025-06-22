const periodTimes = {
    "1限": { start: "09:00", end: "10:30" },
    "2限": { start: "10:40", end: "12:10" },
    "3限": { start: "13:00", end: "14:30" },
    "4限": { start: "14:40", end: "16:10" },
    "5限": { start: "16:20", end: "17:50" }
};

const periodOrder = ["1限", "2限", "3限", "4限", "5限"];

document.querySelectorAll('input[name="periods"]').forEach(cb =>
    cb.addEventListener('change', updateTimeFields)
);

function updateTimeFields() {
    const checked = Array.from(document.querySelectorAll('input[name="periods"]:checked'))
        .map(cb => cb.value)
        .sort((a, b) => periodOrder.indexOf(a) - periodOrder.indexOf(b));

    const container = document.getElementById("timeSlots");
    container.innerHTML = "";

    if (checked.length === 0) return;

    let groups = [];
    let currentGroup = [];

    for (let i = 0; i < checked.length; i++) {
        const current = checked[i];
        const currentIdx = periodOrder.indexOf(current);

        if (
            currentGroup.length === 0 ||
            periodOrder.indexOf(currentGroup[currentGroup.length - 1]) + 1 === currentIdx
        ) {
            currentGroup.push(current);
        } else {
            groups.push(currentGroup);
            currentGroup = [current];
        }
    }
    if (currentGroup.length > 0) {
        groups.push(currentGroup);
    }

    groups.forEach((group, i) => {
        const start = periodTimes[group[0]].start;
        const end = periodTimes[group[group.length - 1]].end;

        const div = document.createElement("div");
        div.className = "mb-2";
        div.innerHTML = `
                    <label class="form-label fw-bold fs-5">利用時間${i + 1}：${start} 〜 ${end}（${group.join("・")}）</label>
                    <input type="hidden" name="timeSlot${i + 1}_start" value="${start}">
                    <input type="hidden" name="timeSlot${i + 1}_end" value="${end}">
                `;
        container.appendChild(div);
    });
}