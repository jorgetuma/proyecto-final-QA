<#include "layout.ftl">
<main>
    <div class="container mt-6">
        <h2>Dashboard de estado de inventario</h2>
        <canvas id="stockChart"></canvas>
    </div>
</main>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<#--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-kXNVqIvZh9j50UAnbFrjPT9O2iNpt5fNr1DHCvKuJdRjBLdqV5NHb+8+8AQy50mp" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    // Datos de ejemplo para el gráfico
    const stockData = {
        labels: [
            'Incrementos de Stock',
            'Decrementos de Stock',
            'Stock Disponible',
            'Cantidad productos'
        ],
        datasets: [{
            data: [${incrementos},${decrementos},${totalStock},${cantProductos}], // Datos
            backgroundColor: [
                '#4caf50', // Verde para incrementos
                '#f44336', // Rojo para decrementos
                '#2196f3', // Azul para stock disponible
                '#ff9800'  // Naranja para cantidad productos
            ]
        }]
    };

    // Configuración del gráfico
    const config = {
        type: 'pie',
        data: stockData,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            const label = tooltipItem.label || '';
                            const value = tooltipItem.raw || 0;
                            return label + ': ' + value + ' unidades';
                        }
                    }
                }
            }
        }
    };

    // Renderiza el gráfico
    window.onload = function() {
        const ctx = document.getElementById('stockChart').getContext('2d');
        new Chart(ctx, config);
    };
</script>