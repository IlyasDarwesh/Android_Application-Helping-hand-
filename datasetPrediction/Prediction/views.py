.import json
import pandas as pd
from scipy import stats
from datetime import datetime, date
from django.http import JsonResponse


def predict(request):
    get_year = request.GET.get('year')
    get_district = request.GET.get('dict')
    print(get_year, get_district)
    date_now = datetime.today().strftime('%Y')
    data = pd.read_csv('dataset.csv')

    year = []
    district = []
    litracy = []
    emp = []
    error = []
    for i in range(len(data)):

        if data.iloc[i]['Distracts'] == get_district:
            # print(int(date_now) - int(data.iloc[i]['Years']))
            year.append(int(date_now) - int(data.iloc[i]['Years']))
            district.append(data.iloc[i]['Distracts'])
            litracy.append(data.iloc[i]['litracy'])
            emp.append(data.iloc[i]['emp'])

    x = year
    y = litracy
    z = emp

    no_of_y = int(get_year) - int(date_now)


    get_value = no_of_y

    slope, intercept, r, p, std_err = stats.linregress(x, y)

    lit_rate = slope * get_value + intercept
    emp_rate = find_emp(x, z, get_value)
    context = {"literacy": lit_rate, "employment": emp_rate}

    return JsonResponse(context)


def find_emp(x, z, value):
    slope, intercept, r, p, std_err = stats.linregress(x, z)
    return slope * value + intercept
