function selectShop() {
    //    alert("Wybrano sklep JS");
            var shopId = $('#shop').val();

    const params = new URLSearchParams({ username: 'Kamil', shopId: shopId });
    const url = '/clients/dashboard?' + params;
    fetch(url)
        // .then(response => response.json())
        .then(data => {
            // handle the response data
            console.log(shopId);
            // reload the page with the fetch URL
            window.location.href = url;

        })
        .catch(error => {
            // handle errors
            console.error('error:', error);
        });
}