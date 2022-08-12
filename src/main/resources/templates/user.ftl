<#import "template.ftl" as layout />
<@layout.mainLayout title="New User">
    <form action="/user" method="post">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name" value="${(user.name)!}">
        </div>
        <div class="form-group">
            <label for="family">Family</label>
            <input type="text" class="form-control" id="family" name="family" placeholder="Enter Family" value="${(user.family)!}">
        </div>
        <div class="form-group">
            <label for="patronymic">Patronymic</label>
            <input type="text" class="form-control" id="patronymic" name="patronymic" placeholder="Enter Patronymic" value="${(user.patronymic)!}">
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Enter Email" value="${(user.email)!}">
        </div>
        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter Phone" value="${(user.phone)!}">
        </div>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="id" name="id" value="${(user.id)!}">
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@layout.mainLayout>