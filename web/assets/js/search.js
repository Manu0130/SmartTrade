async function loadData() {

    const response = await fetch(
            "LoadData"
            );

    const popup = Notification();
    popup.setProperty({
        duration: 5000,
        isHidePrev: true
    });

    if (response.ok) {

        const json = await response.json();

        //load category list
        loadOption("category", json.categoryList, "name");
        //load category list

        //load condition list
        loadOption("condition", json.conditionList, "name");
        //load condition list

        //load color list
        loadOption("color", json.colorList, "name");
        //load color list

        //load storage list
        loadOption("storage", json.storageList, "value");
        //load storage list

        updateProductView(json);

    } else {

        popup.error({
            message: "Try again later"
        });

    }

}

function loadOption(prefix, dataList, property) {

    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";

    dataList.forEach(data => {

        let li_clone = li.cloneNode(true);

        if (prefix == "color") {
            li_clone.style.borderColor = data[property];
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = data[property];
        } else {
            li_clone.querySelector("#" + prefix + "-a").innerHTML = data[property];
        }

        options.append(li_clone);

    });

    //template
    const all_li = document.querySelectorAll('#' + prefix + '-options li');
    all_li.forEach(x => {
        x.addEventListener('click', function () {
            all_li.forEach(y => y.classList.remove('chosen'));
            this.classList.add('chosen');
        });
    });

}

async function searchProducts(firstResult) {

    let category_name = document.getElementById("category-options")
            .querySelector(".chosen")
            ?.querySelector("a").innerHTML;

    let condition_name = document.getElementById("condition-options")
            .querySelector(".chosen")
            ?.querySelector("a").innerHTML;

    let color_name = document.getElementById("color-options")
            .querySelector(".chosen")
            ?.querySelector("a").style.backgroundColor;

    let storage_value = document.getElementById("storage-options")
            .querySelector(".chosen")
            ?.querySelector("a").innerHTML;

    let price_range_start = $('#slider-range').slider('values', 0);
    let price_range_end = $('#slider-range').slider('values', 1);

    let sort_text = document.getElementById("st-sort").value;

    const data = {
        firstResult: firstResult,
        category_name: category_name,
        condition_name: condition_name,
        color_name: color_name,
        storage_value: storage_value,
        price_range_start: price_range_start,
        price_range_end: price_range_end,
        sort_text: sort_text,
    };

    const response = await fetch(
            "SearchProducts",
            {
                method: "POST",
                body: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    const popup = Notification();
    popup.setProperty({
        duration: 5000,
        isHidePrev: true
    });

    if (response.ok) {

        const json = await response.json();
        console.log(json);

        if (json.success) {

            updateProductView(json);

            popup.success({
                message: "Search Complete"
            });
        } else {
            popup.error({
                message: "Search InComplete"
            });
        }

    } else {

        popup.error({
            message: "Try again later"
        });

    }

}

var st_product = document.getElementById("st-product");
var st_pagination_button = document.getElementById("st-pagination-button");

var currentPage = 0;

function updateProductView(json) {

    //load product
    let st_product_container = document.getElementById("st-product-container");
    st_product_container.innerHTML = "";

    json.productList.forEach(product => {
        let st_product_clone = st_product.cloneNode(true);

        st_product_clone.querySelector("#st-product-a").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-img-1").src = "product-images/" + product.id + "/image1.png";
        st_product_clone.querySelector("#st-product-a2").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-title").innerHTML = product.title;
        st_product_clone.querySelector("#st-product-price").innerHTML = "Rs." + new Intl.NumberFormat(
                "en-US",
                {
                    minimumFractionDigits: 2
                }
        ).format(product.price);

        st_product_clone.querySelector("#add-to-cart-main").addEventListener(
                "click",
                (e) => {
            addToCart(product.id, 1);
            e.preventDefault();
        });

        st_product_container.appendChild(st_product_clone);
    });

    //load pagination
    let st_pagination_container = document.getElementById("st-pagination-container");
    st_pagination_container.innerHTML = "";

    let product_count = json.allProductCount;
    const product_per_page = 3;

    let pages = Math.ceil(product_count / product_per_page);

    //add previous arrow
    if (currentPage != 0) {
        let st_pagination_button_clone_prev = st_pagination_button.cloneNode(true);
        st_pagination_button_clone_prev.innerHTML = "Prev";
        st_pagination_button_clone_prev.addEventListener("click", e => {
            currentPage--;
            searchProducts(currentPage * 3);
        });
        st_pagination_container.appendChild(st_pagination_button_clone_prev);
    }


    //add page buttons
    for (let i = 0; i < pages; i++) {
        let st_pagination_button_clone = st_pagination_button.cloneNode(true);
        st_pagination_button_clone.innerHTML = i + 1;

        st_pagination_button_clone.addEventListener("click", e => {
            currentPage = i;
            searchProducts(i * 3);
        });

        if (i == currentPage) {
            st_pagination_button_clone.className = "axil-btn btn-bg-secondary me-2";
        } else {
            st_pagination_button_clone.className = "axil-btn btn-bg-primary me-2";
        }

        st_pagination_container.appendChild(st_pagination_button_clone);
    }

    //add next arrow
    if (currentPage != (pages - 1)) {

        let st_pagination_button_clone_next = st_pagination_button.cloneNode(true);
        st_pagination_button_clone_next.innerHTML = "Next";
        st_pagination_button_clone_next.addEventListener("click", e => {
            currentPage++;
            searchProducts(currentPage * 3);
        });
        st_pagination_container.appendChild(st_pagination_button_clone_next);
    }

}

async function addToCart(id, qty) {

    const response = await fetch(
            "AddToCart?id=" + id + "&qty=" + qty
            );

    if (response.ok) {

        const json = await response.json();

        const popup = Notification();
        popup.setProperty({
            duration: 5000,
            isHidePrev: true
        });

        if (json.success) {

            popup.success({
                message: json.content
            });

        } else {

            popup.error({
                message: json.content
            });

        }

    } else {
        popup.error({
            message: "Unable to process your request"
        });
    }

}