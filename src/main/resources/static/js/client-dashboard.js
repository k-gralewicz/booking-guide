function selectShop() {
//    alert("Wybrano sklep JS");

    const params = new URLSearchParams({ username: 'Kamil'});
        const url = '/visits/create?' + params;
    fetch(url)
        // .then(response => response.json())
        .then(data => {
            // handle the response data
            console.log(data);
                // reload the page with the fetch URL
                window.location.href = url;

        })
        .catch(error => {
            // handle errors
            console.error('error:', error);
        });
}