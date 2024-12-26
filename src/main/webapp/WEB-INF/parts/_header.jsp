<header>
    <nav class="nav-block">
        <div class="nav-block-part-img">
            <a href="/"><img width="50px" height="50px" src="static/img/306214.svg"></a>
        </div>
        <div class="nav-block-part">
            <a href="/">News</a>
        </div>
        <div class="nav-block-part">
            <a href="/advertisements">Adv</a>
        </div>
        <c:if test="${not empty user and user.role == 'ADMIN'}">
            <div class="nav-block-part">
                <a href="/advertisements/check">New Adv</a>
            </div>
        </c:if>
        <div class="nav-block-part">
            <a href="/about">About</a>
        </div>
        <div class="nav-block-profile">
            <a href="/profile">Profile</a>
        </div>
    </nav>
</header>