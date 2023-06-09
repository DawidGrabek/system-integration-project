// import React from 'react'
// import { Line } from 'react-chartjs-2'

// const InflationChart = () => {
//   const inflationData = [
//     1.09999999900013, -0.0989119673600669, 2.47524752475231, 7.05314009565231,
//     2.25631768955089, 4.41306266552888, 4.90278951821563, 8.10126582278465,
//     7.02576112412187, 9.68271334792131, 19.1271820448877, 103.55871886121,
//     25.534759358289, 15.4009994265581, 11.5212607368496, 16.5499681731381,
//     26.3790278536324, 58.7208297320654, 244.550932984827, 567.878800666191,
//     76.7694992087346, 46.0985621037432, 36.9641666120718, 32.9912766093522,
//     27.951388563304, 19.7949669196707, 14.9131586149599, 11.5978554257236,
//     7.1540729117986, 9.90017538843062, 5.40833545566151, 1.90528215048054,
//     0.682701375787657, 3.38264681884694, 2.18379872390258, 1.28469394366409,
//     2.45874312385397, 4.1649719352477, 3.79539242483406, 2.58069370250546,
//     4.23940149625935, 3.56037151702788, 0.99198260633235, 0.0538213132400467,
//     -0.874125874125849, -0.664767331434023, 2.07593553673862, 1.81295156542681,
//     2.227478809383, 3.3744697261859, 5.05502704719268, 14.429,
//   ]

//   const years = Array.from({ length: 52 }, (_, index) => 1971 + index)

//   const labels = years.filter((year, index) => index % 2 === 0)
//   // const data = inflationData.filter((_, index) => index % 2 === 0)

//   const chartData = {
//     labels: labels,
//     datasets: [
//       {
//         label: 'Inflation',
//         data: inflationData,
//         fill: false,
//         borderColor: 'rgb(75, 192, 192)',
//         tension: 0.1,
//         yAxisID: 'inflationScale', // Dodajemy ID skali dla danych inflacji
//       },
//     ],
//   }

//   const chartOptions = {
//     scales: {
//       y: {
//         id: 'inflationScale', // Rejestrujemy nową skalę o ID 'inflationScale'
//         type: 'linear',
//         display: true,
//         position: 'left',
//       },
//     },
//   }

//   return (
//     <div>
//       <Line data={chartData} options={chartOptions} />
//     </div>
//   )
// }

// export default InflationChart
