async function loadFeatures() {

    const response = await fetch(
            "LoadFeatures"
    );

    if (response.ok) {

        const json = await response.json();


        if (json.success) {
      
        } else {

        }


    } else {

        document.getElementById("message").innerHTML = "Please Try Again Later";

    }

}