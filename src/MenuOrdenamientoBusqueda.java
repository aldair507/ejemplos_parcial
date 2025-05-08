import java.util.Arrays;
import java.util.Scanner;

public class MenuOrdenamientoBusqueda {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] original = {34, 7, 23, 32, 5, 62};

        while (true) {
            System.out.println("\n=== MENÚ DE ORDENAMIENTO ===");
            System.out.println("1. Ordenamiento Burbuja");
            System.out.println("2. Ordenamiento Shaker");
            System.out.println("3. Ordenamiento Selección");
            System.out.println("4. Ordenamiento Inserción");
            System.out.println("5. Ordenamiento Shell");
            System.out.println("6. Ordenamiento Heapsort");
            System.out.println("7. Ordenamiento Quicksort");
            System.out.println("8. Ordenamiento MergeSort");
            System.out.println("0. Salir");
            System.out.print("Elige un método de ordenamiento (0-8): ");
            int opcion = sc.nextInt();

            if (opcion == 0) {
                System.out.println("Programa finalizado.");
                break;
            }

            int[] arreglo = original.clone();
            switch (opcion) {
                case 1 -> burbuja(arreglo);
                case 2 -> shakerSort(arreglo);
                case 3 -> seleccion(arreglo);
                case 4 -> insercion(arreglo);
                case 5 -> shellSort(arreglo);
                case 6 -> heapSort(arreglo);
                case 7 -> quickSort(arreglo, 0, arreglo.length - 1);
                case 8 -> mergeSort(arreglo, 0, arreglo.length - 1);
                default -> {
                    System.out.println("Opción inválida.");
                    continue;
                }
            }

            System.out.println("Arreglo ordenado: " + Arrays.toString(arreglo));

            // Preguntar si desea realizar búsqueda binaria
            System.out.print("¿Deseas realizar una búsqueda binaria en este arreglo? (s/n): ");
            char respuesta = sc.next().toLowerCase().charAt(0);

            if (respuesta == 's') {
                System.out.print("Ingresa el número que deseas buscar: ");
                int valor = sc.nextInt();
                int resultado = busquedaBinaria(arreglo, valor);

                if (resultado != -1) {
                    System.out.println("Elemento encontrado en la posición: " + resultado);
                } else {
                    System.out.println("Elemento no encontrado.");
                }
            }
        }

        sc.close();
    }

    // Métodos de ordenamiento
    public static void burbuja(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1])
                    intercambiar(arr, j, j + 1);
    }

    public static void shakerSort(int[] arr) {
        boolean cambiado = true;
        int inicio = 0, fin = arr.length - 1;
        while (cambiado) {
            cambiado = false;
            for (int i = inicio; i < fin; i++)
                if (arr[i] > arr[i + 1]) {
                    intercambiar(arr, i, i + 1);
                    cambiado = true;
                }
            if (!cambiado) break;
            cambiado = false;
            fin--;
            for (int i = fin - 1; i >= inicio; i--)
                if (arr[i] > arr[i + 1]) {
                    intercambiar(arr, i, i + 1);
                    cambiado = true;
                }
            inicio++;
        }
    }

    public static void seleccion(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[min]) min = j;
            intercambiar(arr, i, min);
        }
    }

    public static void insercion(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int clave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > clave) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = clave;
        }
    }

    public static void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2)
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i], j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        for (int i = n - 1; i >= 0; i--) {
            intercambiar(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    public static void heapify(int[] arr, int n, int i) {
        int mayor = i, izq = 2 * i + 1, der = 2 * i + 2;
        if (izq < n && arr[izq] > arr[mayor]) mayor = izq;
        if (der < n && arr[der] > arr[mayor]) mayor = der;
        if (mayor != i) {
            intercambiar(arr, i, mayor);
            heapify(arr, n, mayor);
        }
    }

    public static void quickSort(int[] arr, int inicio, int fin) {
        if (inicio < fin) {
            int pi = particion(arr, inicio, fin);
            quickSort(arr, inicio, pi - 1);
            quickSort(arr, pi + 1, fin);
        }
    }

    public static int particion(int[] arr, int bajo, int alto) {
        int pivote = arr[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++)
            if (arr[j] < pivote)
                intercambiar(arr, ++i, j);
        intercambiar(arr, i + 1, alto);
        return i + 1;
    }

    public static void mergeSort(int[] arr, int izq, int der) {
        if (izq < der) {
            int medio = (izq + der) / 2;
            mergeSort(arr, izq, medio);
            mergeSort(arr, medio + 1, der);
            merge(arr, izq, medio, der);
        }
    }

    public static void merge(int[] arr, int izq, int medio, int der) {
        int[] L = Arrays.copyOfRange(arr, izq, medio + 1);
        int[] R = Arrays.copyOfRange(arr, medio + 1, der + 1);
        int i = 0, j = 0, k = izq;
        while (i < L.length && j < R.length)
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    // Búsqueda Binaria
    public static int busquedaBinaria(int[] arr, int valor) {
        int izq = 0, der = arr.length - 1;
        while (izq <= der) {
            int mid = izq + (der - izq) / 2;
            if (arr[mid] == valor) return mid;
            if (arr[mid] < valor) izq = mid + 1;
            else der = mid - 1;
        }
        return -1;
    }

    // Método auxiliar
    private static void intercambiar(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
