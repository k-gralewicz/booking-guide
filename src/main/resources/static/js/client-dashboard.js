function selectShopOrService() {
    //    alert("Wybrano sklep JS");
            var shopId = $('#shop').val();
            var serviceId = $('#service').val();

    const params = new URLSearchParams({ username: 'Kamil', selectedShopId: shopId, selectedServiceId: serviceId });
    const url = '/clients/dashboard?' + params;
    fetch(url)
        // .then(response => response.json())
        .then(data => {
            // handle the response data
            console.log(url);
            console.log(shopId);
            console.log(serviceId);
            // reload the page with the fetch URL
            window.location.href = url;

        })
        .catch(error => {
            // handle errors
            console.error('error:', error);
        });
}