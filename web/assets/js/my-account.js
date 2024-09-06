async function loadFeatures() {

    const response = await fetch(
            "LoadFeatures"
    );

    if (response.ok) {

        const json = await response.json();
        console.log(json);

    } else {

        document.getElementById("message").innerHTML = "Please Try Again Later";

    }

}