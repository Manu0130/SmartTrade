async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("id")) {
        const productId = parameters.get("id");
            console.log(productId);

        const response = await fetch("LoadSingleProduct?id=" + productId);

        if (response.ok) {

            const json = await response.json();
            console.log(json.product.id);
            
            
//            const id = json.product.id;
//            document.getElementById("image1").src="product-images/"+id+"/image1.png/";
            

        } else {
            window.location = "index.html";
        } 
    } else {
        window.location = "index.html";
    }

}