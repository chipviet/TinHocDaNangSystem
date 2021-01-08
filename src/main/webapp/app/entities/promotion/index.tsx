import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Promotion from './promotion';
import PromotionDetail from './promotion-detail';
import PromotionUpdate from './promotion-update';
import PromotionDeleteDialog from './promotion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PromotionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PromotionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PromotionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Promotion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PromotionDeleteDialog} />
  </>
);

export default Routes;
