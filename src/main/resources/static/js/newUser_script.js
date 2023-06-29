const newUser = document.getElementById("formNewUser")
const newRole = document.querySelector("#roleNew").selectedOptions


newUser.addEventListener("submit", save => {
    save.preventDefault()
    let rolesList = []
    for (let i = 0; i < newRole.length; i++) {
        rolesList.push({
            id: newRole[i].value,
            name: "ROLE_" + newRole[i].text
        })
    }
    fetch('/api/admin', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            userName: newUser.userName.value,
            lastName: newUser.lastName.value,
            age: newUser.age.value,
            email: newUser.email.value,
            password: newUser.password.value,
            roles: rolesList
        })
    }).then(() => {
        newUser.reset()
        adminPage()
        change_tab()
    })
})

function change_tab() {
    const newUserTab = document.getElementById('tab-link-2');
    const newUser = document.getElementById('tabp-2');
    newUserTab.setAttribute('class', 'nav-link');
    newUser.setAttribute('class', 'tab-pane');

    const userListTab = document.getElementById('tab-link-1');
    const userList = document.getElementById('tabp-1');
    userListTab.setAttribute('class', 'nav-link active show');
    userList.setAttribute('class', 'tab-pane active show');

}