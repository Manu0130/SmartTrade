async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("id")) {
        const productId = parameters.get("id");
        console.log(productId);

        const response = await fetch("LoadSingleProduct?id=" + productId);

        if (response.ok) {

            const json = await response.json();

            console.log(json.product.id);

            const id = json.product.id;
            document.getElementById("image1").src = "product-images/" + id + "/image1.png";
            document.getElementById("image2").src = "product-images/" + id + "/image2.png";
            document.getElementById("image3").src = "product-images/" + id + "/image3.png";

            document.getElementById("image1-thumb").src = "product-images/" + id + "/image1.png";
            document.getElementById("image2-thumb").src = "product-images/" + id + "/image2.png";
            document.getElementById("image3-thumb").src = "product-images/" + id + "/image3.png";

            document.getElementById("product-title").innerHTML = json.product.title;
            document.getElementById("product-published-on").innerHTML = json.product.date_time;

            document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(json.product.price);

            document.getElementById("product-category").innerHTML = json.product.model.category.name;
            document.getElementById("product-model").innerHTML = json.product.model.name;
            document.getElementById("product-condition").innerHTML = json.product.product_condition.name;
            document.getElementById("product-qty").innerHTML = json.product.qty;

            document.getElementById("color-border").style.borderColor = json.product.color.name;
            document.getElementById("color-background").style.backgroundColor = json.product.color.name;

            document.getElementById("product-storage").innerHTML = json.product.storage.value;
            document.getElementById("product-description").innerHTML = json.product.description;

            let ProductHtml = document.getElementById("similer-product");
            document.getElementById("similer-product-main").innerHTML = "";

            json.productList.forEach(item => {
                console.log(item.title);
                
                let productCloneHtml = ProductHtml.cloneNode(true);

                productCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similer-product-image").src = "product-images/" + item.id + "/image1.png";
                productCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
                productCloneHtml.querySelector("#similer-product-storage").innerHTML = item.storage.value;
                productCloneHtml.querySelector("#similer-product-price").innerHTML = "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.price);
                productCloneHtml.querySelector("#similer-product-color-border").style.borderColor = item.color.name;
                productCloneHtml.querySelector("#similer-product-color").style.backgroundColor = item.color.name;

                document.getElementById("similer-product-main").appendChild(productCloneHtml);
                //clone කරපු එකක් ඇතුලේ තියෙන එකක් ගන්නවා නම් query selector

            });

            $('.recent-product-activation').slick({
                infinite: true,
                slidesToShow: 4,
                slidesToScroll: 4,
                arrows: true,
                dots: false,
                prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
                nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
                responsive: [{
                        breakpoint: 1199,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3
                        }
                    },
                    {
                        breakpoint: 991,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 2
                        }
                    },
                    {
                        breakpoint: 479,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1
                        }
                    }
                ]
            });


        } else {
            window.location = "index.html";
        }
    } else {
        window.location = "index.html";
    }

}